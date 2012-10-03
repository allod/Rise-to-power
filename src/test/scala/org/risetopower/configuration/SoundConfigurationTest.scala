package org.risetopower.configuration

import org.scalatest.FunSuite
import io.Codec
import xml.PrettyPrinter

class SoundConfigurationTest extends FunSuite {

  test("Creating SoundConfiguration from xml") {
    val expectedConfiguration = SoundConfiguration()

    val xmlNodes = <sound></sound>

    val actualConfiguration = SoundConfiguration.fromXml(xmlNodes)

    assert(actualConfiguration === expectedConfiguration)
  }
  test("Serialization to xml") {
    val expectedXmlNodes = <sound></sound>

    val configuration = SoundConfiguration()

    val actualXmlNodes= configuration.toXml

    val xmlPrettyPrinter = new PrettyPrinter(80, 2)
    assert(xmlPrettyPrinter.format(actualXmlNodes) === xmlPrettyPrinter.format(expectedXmlNodes))
  }

}
