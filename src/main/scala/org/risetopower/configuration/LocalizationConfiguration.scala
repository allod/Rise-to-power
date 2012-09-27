package org.risetopower.configuration

import org.risetopower.util.serialization.{CanReadXML, CanWriteXML}
import xml.{Elem, NodeSeq}
import java.nio.charset.Charset

object LocalizationConfiguration extends CanReadXML[LocalizationConfiguration] {

  def fromXml(node: NodeSeq) = LocalizationConfiguration(
    messageFileEncoding = Charset forName (node \ "messageFileEncoding").text,
    defaultLanguage = (node \ "defaultLanguage").text,
    currentLanguage = (node \ "currentLanguage").text,
    filenamePattern = (node \ "filenamePattern").text,
    supportedLanguages = (node \ "supportedLanguages" \ "language").toSeq.map(_.text)
  )
}

case class LocalizationConfiguration(
    messageFileEncoding: Charset,
    defaultLanguage: String,
    currentLanguage: String,
    filenamePattern: String,
    supportedLanguages: Seq[String]) extends CanWriteXML[LocalizationConfiguration] {

  def toXml =
    <localization>
      <messageFileEncoding>{messageFileEncoding}</messageFileEncoding>
      <defaultLanguage>{defaultLanguage}</defaultLanguage>
      <currentLanguage>{currentLanguage}</currentLanguage>
      <filenamePattern>{filenamePattern}</filenamePattern>
      <supportedLanguages>{supportedLanguagesToXml}</supportedLanguages>
    </localization>

  private def supportedLanguagesToXml = {
    for (lang <- supportedLanguages)
      yield <language>{lang}</language>
  }
}
