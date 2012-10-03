package org.risetopower.configuration

import org.scalatest.FunSuite
import io.Codec
import xml.PrettyPrinter

class GraphicsConfigurationTest extends FunSuite {

  test("Creating GraphicsConfiguration from xml") {
    val expectedConfiguration = GraphicsConfiguration()

    val xmlNodes = <graphics></graphics>

    val actualConfiguration = GraphicsConfiguration.fromXml(xmlNodes)

    assert(actualConfiguration === expectedConfiguration)
  }
  test("Serialization to xml") {
    val expectedXmlNodes = <graphics></graphics>

    val configuration = GraphicsConfiguration()

    val actualXmlNodes= configuration.toXml

    val xmlPrettyPrinter = new PrettyPrinter(80, 2)
    assert(xmlPrettyPrinter.format(actualXmlNodes) === xmlPrettyPrinter.format(expectedXmlNodes))
  }

}
