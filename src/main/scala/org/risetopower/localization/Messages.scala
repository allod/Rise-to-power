package org.risetopower.localization

import scala.collection.immutable.Map
import java.net.URL
import org.risetopower.util.Logging

object Messages extends Logging{

  lazy val messages: Map[String, String] = readMessages()

  private val DEFAULT_MESSAGES = "messages"
  private val MESSAGE_SEPARATOR = "="
  private val PARAM_HOLDER = "%s"


  def get(key: String, params: Any*) = messages.get(key) match {
    case Some(message) => params.foldLeft(message) { (m, p) => m.replaceFirst(PARAM_HOLDER, p.toString) }
    case None => key
  }


  private def readMessages(): Map[String, String] = {
    logger.debug("loading messages from: " + DEFAULT_MESSAGES)

    val name: URL = getClass.getClassLoader.getResource(DEFAULT_MESSAGES)
    val lines = scala.io.Source.fromFile(name.toURI).getLines()

    def parseKey(line: String): String = line.take(line.indexOf(MESSAGE_SEPARATOR))
    def parseMessage(line: String): String = line.drop(line.indexOf(MESSAGE_SEPARATOR) + MESSAGE_SEPARATOR.size)

    lines.map(line => (parseKey(line), parseMessage(line))).toMap
  }

}
