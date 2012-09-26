package org.risetopower.configuration

import xml.XML
import io.Codec

object GameSettings {
  var localizationConfiguration: LocalizationConfiguration = _

  def loadSettings(path: String) {
    val xmlDocument = XML.loadFile(path)
    localizationConfiguration = LocalizationConfiguration.fromXml(xmlDocument \ "localization")
  }

  def saveSettings(path: String) {
    val xmlDocument =
      <settings>
        {localizationConfiguration.toXml}
      </settings>

    XML.save(path, xmlDocument, localizationConfiguration.fileEncoding.toString, xmlDecl = true)
  }

  def resetToDefaults() {
    localizationConfiguration = LocalizationConfiguration(
      fileEncoding = Codec.UTF8,
      defaultLanguage = "en",
      currentLanguage = "en",
      filenamePattern = "localization/messages",
      supportedLanguages = Seq("en", "uk", "ru")
    )
  }
}
