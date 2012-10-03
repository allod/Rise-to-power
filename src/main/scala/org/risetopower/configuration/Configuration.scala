package org.risetopower.configuration

import xml.XML
import io.{Source, Codec}

object Configuration {
  var game: GameConfiguration = _
  var graphics: GraphicsConfiguration = _
  var sound: SoundConfiguration = _
  var localization: LocalizationConfiguration = _

  private val SettingsFileEncoding = Codec.UTF8.toString

  def load(path: String) {
    val xmlDocument = XML.loadFile(path)

    game = GameConfiguration.fromXml(xmlDocument \ "game")
    graphics = GraphicsConfiguration.fromXml(xmlDocument \ "graphics")
    sound = SoundConfiguration.fromXml(xmlDocument \ "sound")
    localization = LocalizationConfiguration.fromXml(xmlDocument \ "localization")
  }

  def save(path: String) {
    val xmlDocument =
      <settings>
        {game.toXml}
        {graphics.toXml}
        {sound.toXml}
        {localization.toXml}
      </settings>

    XML.save(path, xmlDocument, SettingsFileEncoding, xmlDecl = true)
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
