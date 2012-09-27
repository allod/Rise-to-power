package org.risetopower.configuration

import xml.XML
import io.{Source, Codec}

object GameSettings {
  var localization: LocalizationConfiguration = _
  private val SettingsFileEncoding = Codec.UTF8.toString

  def loadSettings(path: String) {
    val xmlDocument = XML.loadFile(path)
    localization = LocalizationConfiguration.fromXml(xmlDocument \ "localization")
  }

  def saveSettings(path: String) {
    val xmlDocument =
      <settings>
        {localization.toXml}
      </settings>

    XML.save(path, xmlDocument, SettingsFileEncoding, xmlDecl = true)
  }

  def resetToDefaults() {
    localization = LocalizationConfiguration(
      messageFileEncoding = Codec.UTF8,
      defaultLanguage = "en",
      currentLanguage = "en",
      filenamePattern = "localization/messages",
      supportedLanguages = Seq("en", "uk", "ru")
    )
  }
}
