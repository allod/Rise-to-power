package org.risetopower.localization

import org.scalatest.FunSuite

class MessagesTest extends FunSuite {

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
}
