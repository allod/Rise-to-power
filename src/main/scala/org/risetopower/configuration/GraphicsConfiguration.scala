package org.risetopower.configuration

import org.risetopower.util.serialization.{CanWriteXML, CanReadXML}
import xml.NodeSeq

object GraphicsConfiguration extends Configuration with CanReadXML[GraphicsConfiguration] {
  def fromXml(seq: NodeSeq) = GraphicsConfiguration()
}

case class GraphicsConfiguration() extends  Configuration with CanWriteXML[GraphicsConfiguration] {
  def toXml = <graphics></graphics>
}