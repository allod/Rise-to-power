package org.risetopower.configuration

import org.scalatest.FunSuite
import io.Codec
import xml.PrettyPrinter

class GameConfigurationTest extends FunSuite {

  test("Creating GameConfiguration from xml") {
    val expectedConfiguration = GameConfiguration()

    val xmlNodes = <game></game>

    val actualConfiguration = GameConfiguration.fromXml(xmlNodes)

    assert(actualConfiguration === expectedConfiguration)
  }

  test("Serialization to xml") {
    val expectedXmlNodes = <game></game>

    val configuration = GameConfiguration()

    val actualXmlNodes= configuration.toXml

    val xmlPrettyPrinter = new PrettyPrinter(80, 2)
    assert(xmlPrettyPrinter.format(actualXmlNodes) === xmlPrettyPrinter.format(expectedXmlNodes))
  }

}
