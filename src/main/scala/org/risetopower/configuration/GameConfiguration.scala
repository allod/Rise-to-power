package org.risetopower.configuration

import org.risetopower.util.serialization.{CanWriteXML, CanReadXML}
import xml.NodeSeq

object GameConfiguration extends Configuration with CanReadXML[GameConfiguration] {
  def fromXml(seq: NodeSeq) = GameConfiguration()
}

case class GameConfiguration() extends  Configuration with CanWriteXML[GameConfiguration] {
  def toXml = <game></game>
}