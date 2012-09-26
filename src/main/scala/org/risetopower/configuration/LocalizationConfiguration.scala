package org.risetopower.configuration

import org.risetopower.util.serialization.{CanReadXML, CanWriteXML}
import xml.NodeSeq
import java.nio.charset.Charset

object LocalizationConfiguration extends CanReadXML[LocalizationConfiguration] {

  def fromXml(node: NodeSeq) = {
    val encoding = Charset forName (node \ "fileEncoding").text
    val defaultLang = (node \ "defaultLanguage").text
    val currentLang = (node \ "currentLanguage").text
    val filenamePattern = (node \ "filenamePattern").text
    val languages = Seq("en")

    LocalizationConfiguration(encoding, defaultLang, currentLang, filenamePattern, languages)
  }
}

case class LocalizationConfiguration(
  fileEncoding: Charset,
  defaultLanguage: String,
  currentLanguage: String,
  filenamePattern: String,
  supportedLanguages: Seq[String]) extends CanWriteXML[LocalizationConfiguration] {

  def toXml =
    <localization>
      <fileEncoding>{fileEncoding}</fileEncoding>
      <defaultLanguage>{defaultLanguage}</defaultLanguage>
      <currentLanguage>{currentLanguage}</currentLanguage>
      <filenamePattern>{filenamePattern}</filenamePattern>
    </localization>
}
