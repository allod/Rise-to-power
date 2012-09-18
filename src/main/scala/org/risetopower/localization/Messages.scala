package org.risetopower.localization

import java.net.URI
import org.risetopower.util.Logging
import io.Source

object Messages extends Logging {

  lazy val messages: Map[String, Map[String, String]] = initMessages()

  private val MessageSeparator = "="
  private val ParamHolder = "%s"

  //TODO move to Configuration
  private val defaultLanguage = "en"
  private val currentLanguage = "en"
  private val filenamePattern = "localization/messages"
  private val languages = List("en", "uk", "ru")


  def get(key: String, params: Any*)(implicit lang: String = currentLanguage): String = messages.get(lang) match {
    case Some(messageMap) => getMessage(messageMap, key, params:_*)
    case None => get(key, params:_*)(defaultLanguage)
  }

  private def getMessage(messageMap: Map[String, String], key: String, params: Any*) = messageMap.get(key) match {
    case Some(message) => putParamsInMessage(message, params:_*)
    case None => key
  }

  private def putParamsInMessage(message: String, params: Any*) =
    params.foldLeft(message) {(msg, p) => msg.replaceFirst(ParamHolder, p.toString)}

  private def initMessages(): Map[String, Map[String, String]] = {
    logger.debug("loading messages for following languages: " + languages.mkString(","))

    val loadedMessages = languages.foldLeft(Map.empty[String, Map[String, String]]) {
      (map, lang) => map + (lang -> parseMessages(lang))
    }

    loadedMessages foreach {case (lang, map) => logger.info("For language {} found {} messages", lang, map.size)}
    loadedMessages
  }


  private def parseMessages(language: String): Map[String, String] = {
    val fileName = if (language == defaultLanguage) filenamePattern else filenamePattern + "." + language
    val resource = Option(getClass.getClassLoader.getResource(fileName))

    resource match {
      case Some(resourceUrl) => {
        logger.info("File {} for language {} found, parsing file", fileName, language)
        parseLines(resourceUrl.toURI)
      }
      case None => {
        logger.warn("{} for language {} not found, using empty map", fileName, language)
        Map.empty[String, String]
      }
    }
  }

  private def parseLines(resourceUri: URI) = {
    implicit def parseLine(line: String) = new {
      def separatorIndex = line.indexOf(MessageSeparator)
      def key = line.take(separatorIndex)
      def message = line.drop(separatorIndex + MessageSeparator.size)
    }

    val lines = Source.fromFile(resourceUri).getLines()
    lines.map(line => (line.key, line.message)).toMap
  }
}
