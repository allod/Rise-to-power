package org.risetopower.configuration

import org.scalatest.{BeforeAndAfter, FunSuite}
import java.nio.file.{Paths, Path, Files}
import io.Codec
import xml.XML
import java.io.File

class ConfigurationTest extends FunSuite with BeforeAndAfter {
  val SettingsToLoadUri = getClass.getClassLoader.getResource("settings/uk_settings.xml").toURI
  val SettingsToSaveUri = getClass.getClassLoader.getResource("settings").getPath + File.separator + "saved_settings.xml"

  after {
    Files.deleteIfExists(Paths.get(SettingsToSaveUri))
  }

  test("Loading configuration from file") {
    Configuration.load(Paths.get(SettingsToLoadUri))

    assert(Configuration.game === GameConfiguration())

    assert(Configuration.graphics === GraphicsConfiguration())

    assert(Configuration.sound === SoundConfiguration())

    assert(Configuration.localization.currentLanguage === "uk")
    assert(Configuration.localization.defaultLanguage === "uk")
    assert(Configuration.localization.messageFileEncoding === Codec.ISO8859)
    assert(Configuration.localization.filenamePattern === "custom/messages")
    assert(Configuration.localization.supportedLanguages === Seq("uk", "de", "fr"))
  }

  test("Resetting configuration to default values") {
    Configuration.load(Paths.get(SettingsToLoadUri))

    Configuration.resetToDefaults()

    assert(Configuration.game === GameConfiguration())

    assert(Configuration.graphics === GraphicsConfiguration())

    assert(Configuration.sound === SoundConfiguration())

    assert(Configuration.localization.currentLanguage === "en")
    assert(Configuration.localization.defaultLanguage === "en")
    assert(Configuration.localization.messageFileEncoding === Codec.UTF8)
    assert(Configuration.localization.filenamePattern === "localization/messages")
    assert(Configuration.localization.supportedLanguages === Seq("en", "uk", "ru"))
  }

  test("Saving configuration to xml file") {
    Configuration.game = GameConfiguration()

    Configuration.graphics = GraphicsConfiguration()

    Configuration.sound = SoundConfiguration()

    Configuration.localization = LocalizationConfiguration(
      messageFileEncoding = Codec.ISO8859,
      defaultLanguage = "fr",
      currentLanguage = "de",
      filenamePattern = "messages",
      supportedLanguages = Seq("de", "fr")
    )

    Configuration.save(Paths.get(SettingsToSaveUri))

    val savedFile = Paths.get(SettingsToSaveUri).toFile
    assert(savedFile.exists)

    val xml = XML.loadFile(savedFile)
    assert(xml.label === "settings")

    assert((xml \ "game" ).text.isEmpty)

    assert((xml \ "graphics" ).text.isEmpty)

    assert((xml \ "sound" ).text.isEmpty)

    assert((xml \ "localization" \ "defaultLanguage").text === "fr")
    assert((xml \ "localization" \ "currentLanguage").text === "de")
    assert((xml \ "localization" \ "messageFileEncoding").text === "ISO-8859-1")
    assert((xml \ "localization" \ "filenamePattern").text === "messages")
    assert((xml \ "localization" \ "supportedLanguages" \ "language").toSeq.map(_.text) === Seq("de", "fr"))
  }

}
