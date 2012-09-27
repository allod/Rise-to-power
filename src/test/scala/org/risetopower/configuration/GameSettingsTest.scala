package org.risetopower.configuration

import org.scalatest.{BeforeAndAfter, FunSuite}
import java.nio.file.{Paths, Path, Files}
import io.Codec
import xml.XML

class GameSettingsTest extends FunSuite with BeforeAndAfter {
  val SettingsToLoadFilePath = "src/test/resources/uk_settings.xml"
  val SettingsToSaveFilePath = "src/test/resources/settings.xml"

  after {
    Files.deleteIfExists(Paths.get(SettingsToSaveFilePath))
  }

  test("Check settings loading from file") {
    GameSettings.loadSettings(SettingsToLoadFilePath)

    assert(GameSettings.localization.currentLanguage === "uk")
    assert(GameSettings.localization.defaultLanguage === "uk")
    assert(GameSettings.localization.messageFileEncoding === Codec.ISO8859)
    assert(GameSettings.localization.filenamePattern === "custom/messages")
    assert(GameSettings.localization.supportedLanguages === Seq("uk", "de", "fr"))
  }

  test("Check settings resetting to default values") {
    GameSettings.loadSettings(SettingsToLoadFilePath)

    GameSettings.resetToDefaults()

    assert(GameSettings.localization.currentLanguage === "en")
    assert(GameSettings.localization.defaultLanguage === "en")
    assert(GameSettings.localization.messageFileEncoding === Codec.UTF8)
    assert(GameSettings.localization.filenamePattern === "localization/messages")
    assert(GameSettings.localization.supportedLanguages === Seq("en", "uk", "ru"))
  }

  test("Check settings saving to xml file") {
    val localizationConfiguration = LocalizationConfiguration(
      messageFileEncoding = Codec.ISO8859,
      defaultLanguage = "fr",
      currentLanguage = "de",
      filenamePattern = "messages",
      supportedLanguages = Seq("de", "fr")
    )
    GameSettings.localization = localizationConfiguration

    GameSettings.saveSettings(SettingsToSaveFilePath)

    val savedFile = Paths.get(SettingsToSaveFilePath).toFile
    assert(savedFile.exists)

    val xml = XML.loadFile(savedFile)
    assert(xml.label === "settings")
    assert((xml \ "localization" \ "defaultLanguage").text === "fr")
    assert((xml \ "localization" \ "currentLanguage").text === "de")
    assert((xml \ "localization" \ "messageFileEncoding").text === "ISO-8859-1")
    assert((xml \ "localization" \ "filenamePattern").text === "messages")
    assert((xml \ "localization" \ "supportedLanguages" \ "language").toSeq.map(_.text) === Seq("de", "fr"))
  }

}
