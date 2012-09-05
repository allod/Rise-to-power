package org.risetopower

import org.scalatest.FunSuite
import collection.mutable

class ScalaTestExample extends FunSuite {

  test("pop is invoked on a non-empty stack") {

    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    val oldSize = stack.size
    val result = stack.pop()
    assert(result === 2)
    assert(stack.size === oldSize - 1)
  }

  test("pop is invoked on an empty stack") {

    val emptyStack = new mutable.Stack[Int]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
    assert(emptyStack.isEmpty)
  }
}
