package org.risetopower.util.serialization

import xml.{NodeSeq, Node}

trait CanReadXML[A] {
  def fromXml(seq: NodeSeq): A
}

trait CanWriteXML[A] {
  def toXml: Node
}