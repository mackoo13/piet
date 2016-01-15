package piet

import org.scalatest.FunSuite

class PietStackTest extends FunSuite {

  test("pushOnlyInt should not push the string if it does not represent an integer") {
    val stack = new PietStack()
    stack.pushOnlyInt("a")
    assert(stack.stack.isEmpty)
  }

  test("pushOnlyInt should push the string if it correctly represents an integer") {
    val stack = new PietStack()
    stack.pushOnlyInt("42")
    assert(stack.stack.pop == 42)
  }

  test("pushOnlyChar should not push the string if it is longer than one character") {
    val stack = new PietStack()
    stack.pushOnlyChar("ab")
    assert(stack.stack.isEmpty)
  }

  test("pushOnlyChar should push the char and convert it to integer") {
    val stack = new PietStack()
    stack.pushOnlyChar("1")
    assert(stack.stack.pop == 49)
  }

  test("pushOnlyInt should not push the string if it does not represent an integer") {
    val stack = new PietStack()
    stack.pushOnlyInt("a")
    assert(stack.stack.isEmpty)
  }

//  test("add should do nothing when stack.length<2") {
//    val stack = new PietStack()
//    stack.stack.push(42)
//    stack.add()
//    assert(stack.stack.length == 1 && stack.stack.pop == 42)
//  }

  test("pop should be ignored when the stack is empty") {
    val stack = new PietStack()
    stack.pop
    assert(stack.stack.isEmpty)
  }

  test("")
}