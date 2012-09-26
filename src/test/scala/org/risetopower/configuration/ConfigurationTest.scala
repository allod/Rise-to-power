package org.risetopower.configuration

import org.scalatest.{BeforeAndAfter, FunSuite}
import java.nio.file.{Paths, Path, Files}

class ConfigurationTest extends FunSuite with BeforeAndAfter {
  val FilePath = "src/test/resources/settings.xml"

  after {
    Files.delete(Paths.get(FilePath))
  }

  test("Get message without parameters") {
    GameSettings.resetToDefaults()
    GameSettings.saveSettings(FilePath)
    //TODO write tests
  }

}
