package org.risetopower.configuration

import org.risetopower.util.serialization.{CanWriteXML, CanReadXML}
import xml.NodeSeq

object SoundConfiguration extends CanReadXML[SoundConfiguration] {
  def fromXml(seq: NodeSeq) = SoundConfiguration()
}

case class SoundConfiguration() extends  Configuration with CanWriteXML[SoundConfiguration] {
  def toXml = <sound></sound>
}
