package piet

import org.scalatest._

class PietStackTest extends FunSuite {

  test("pushOnlyInt should not push the string if it does not represent an integer") {
    val stack = new PietStack()
    stack.pushOnlyInt("a")
    assert(stack.isEmpty)
  }

  test("pushOnlyInt should push the string if it correctly represents an integer") {
    val stack = new PietStack()
    stack.pushOnlyInt("42")
    assert(stack.pop == 42)
  }

  test("pushOnlyChar should not push the string if it is longer than one character") {
    val stack = new PietStack()
    stack.pushOnlyChar("ab")
    assert(stack.isEmpty)
  }

  test("pushOnlyChar should push the char and convert it to integer") {
    val stack = new PietStack()
    stack.pushOnlyChar("1")
    assert(stack.pop == 49)
  }

  test("pop should be ignored when the stack is empty") {
    val stack = new PietStack()
    stack.pop
    assert(stack.isEmpty)
  }

  test("add should do nothing when stack.length<2") {
    val stack = new PietStack()
    stack.push(42)
    stack.add
    assert(stack.length == 1 && stack.pop == 42)
  }

  test("add should correctly add 2 numbers if stack.length > 2") {
    val stack = new PietStack()
    stack.push(42)
    stack.push(42)
    stack.add
    assert(stack.length == 1)
    assert(stack.pop == 84)
  }

  test("div should execute correctly when 2 or more values on stack"){
    val stack = new PietStack
    stack push 42
    stack push 5
    stack.div
    assertResult(8)(stack.pop)
  }

  test("div should do nothing when one value on stack") {
    val stack = new PietStack
    stack push 42
    stack.div
    assertResult(42)(stack.pop)
    assert(stack.isEmpty)
  }

  test("div should do nothing when no values on stack") {
    val stack = new PietStack
    stack.div
    assert(stack.isEmpty)
  }
}