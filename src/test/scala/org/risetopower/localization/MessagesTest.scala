package org.risetopower.localization

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.risetopower.configuration.Configuration

class MessagesTest extends FunSuite with BeforeAndAfter{

  before {
    Configuration.load()
  }

  test("Get message without parameters") {
    val actualMessage: String = Messages.get("default.message")

    assert(actualMessage === "default message")
  }

  test("Get message with one parameter") {
    val actualMessage: String = Messages.get("hello.world", "world")

    assert(actualMessage === "Hello world!")
  }

  test("Get message with two parameters of different types") {
    val actualMessage: String = Messages.get("new.mail.received", "Harry", 2)

    assert(actualMessage === "Hi Harry, you have 2 new emails")
  }

  test("Get not existing message") {
    val actualMessage: String = Messages.get("not.existing.message")

    assert(actualMessage === "not.existing.message")
  }

  test("Get message with explicit language") {
    val actualMessage: String = Messages.get("default.message")("en")

    assert(actualMessage === "default message")
  }

  test("Get message for different language") {
    val actualMessage: String = Messages.get("default.message")("uk")

    assert(actualMessage === "Повідомлення за замовчуванням")
  }

  test("Get missing message with existing default language message") {
    val actualMessage: String = Messages.get("default.message")("ru")

    assert(actualMessage === "default.message")
  }

  test("Get missing message with missing default language message") {
    val actualMessage: String = Messages.get("not.existing.message")("ru")

    assert(actualMessage === "not.existing.message")
  }

  test("Get message for unsupported language") {
    val actualMessage: String = Messages.get("hello.world", "from France")("fr")

    assert(actualMessage === "Hello from France!")
  }

  test("Get missing message for unsupported language") {
    val actualMessage: String = Messages.get("german.message")("de")

    assert(actualMessage === "german.message")
  }
}
