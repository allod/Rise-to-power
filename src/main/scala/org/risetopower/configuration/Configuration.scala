package org.risetopower.configuration

import xml.XML
import io.{Source, Codec}
import java.nio.file.{Path, Paths}
import java.io.File

object Configuration {
  var game: GameConfiguration = _
  var graphics: GraphicsConfiguration = _
  var sound: SoundConfiguration = _
  var localization: LocalizationConfiguration = _

  private val SettingsFileEncoding = Codec.UTF8.toString
  private val SettingsDirectory = "settings"
  private val SettingsFile = "settings.xml"

  private val SettingsUri = getClass.getClassLoader.getResource(SettingsDirectory + File.separator + SettingsFile).toURI

  load()

  def load(path: Path = Paths.get(SettingsUri)) {
    val xmlDocument = XML.loadFile(path.toFile)

    game = GameConfiguration.fromXml(xmlDocument \ "game")
    graphics = GraphicsConfiguration.fromXml(xmlDocument \ "graphics")
    sound = SoundConfiguration.fromXml(xmlDocument \ "sound")
    localization = LocalizationConfiguration.fromXml(xmlDocument \ "localization")
  }

  def save(path: Path) {
    val xmlDocument =
      <settings>
        {game.toXml}
        {graphics.toXml}
        {sound.toXml}
        {localization.toXml}
      </settings>

    XML.save(path.toString, xmlDocument, SettingsFileEncoding, xmlDecl = true)
  }

  def resetToDefaults() {
    game = GameConfiguration()

    graphics = GraphicsConfiguration()

    sound = SoundConfiguration()

    localization = LocalizationConfiguration(
      messageFileEncoding = Codec.UTF8,
      defaultLanguage = "en",
      currentLanguage = "en",
      filenamePattern = "localization/messages",
      supportedLanguages = Seq("en", "uk", "ru")
    )
  }
}

abstract class Configuration
