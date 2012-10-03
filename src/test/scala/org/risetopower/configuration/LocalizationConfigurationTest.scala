package org.risetopower.configuration

import org.scalatest.FunSuite
import io.Codec
import xml.PrettyPrinter

class LocalizationConfigurationTest extends FunSuite {

  test("Creating LocalizationConfiguration from xml") {
    val expectedConfiguration = LocalizationConfiguration(
      messageFileEncoding = Codec.ISO8859,
      defaultLanguage = "ru",
      currentLanguage = "de",
      filenamePattern = "localization/messages",
      supportedLanguages = Seq("uk", "de", "ru")
    )

    val xmlNodes =
      <localization>
        <messageFileEncoding>ISO-8859-1</messageFileEncoding>
        <defaultLanguage>ru</defaultLanguage>
        <currentLanguage>de</currentLanguage>
        <filenamePattern>localization/messages</filenamePattern>
        <supportedLanguages>
          <language>uk</language>
          <language>de</language>
          <language>ru</language>
        </supportedLanguages>
      </localization>

    val actualConfiguration = LocalizationConfiguration.fromXml(xmlNodes)

    assert(actualConfiguration === expectedConfiguration)
  }
  test("Serialization to xml") {
    val expectedXmlNodes =
      <localization>
        <messageFileEncoding>UTF-8</messageFileEncoding>
        <defaultLanguage>en</defaultLanguage>
        <currentLanguage>fr</currentLanguage>
        <filenamePattern>localization/messages</filenamePattern>
        <supportedLanguages>
          <language>en</language>
          <language>de</language>
          <language>fr</language>
        </supportedLanguages>
      </localization>

    val configuration = LocalizationConfiguration(
      messageFileEncoding = Codec.UTF8,
      defaultLanguage = "en",
      currentLanguage = "fr",
      filenamePattern = "localization/messages",
      supportedLanguages = Seq("en", "de", "fr")
    )

    val actualXmlNodes= configuration.toXml

    val xmlPrettyPrinter = new PrettyPrinter(80, 2)
    assert(xmlPrettyPrinter.format(actualXmlNodes) === xmlPrettyPrinter.format(expectedXmlNodes))
  }

}
