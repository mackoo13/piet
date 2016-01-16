package piet

import org.scalatest._

class PietStackTest extends FunSuite {

  test("pushOnlyInt should not push the string if it does not represent an integer.") {
    val stack = new PietStack()
    stack.pushOnlyInt("a")
    assert(stack.isEmpty)
  }

  test("pushOnlyInt should push the string if it correctly represents an integer.") {
    val stack = new PietStack()
    stack.pushOnlyInt("42")
    assert(stack.pop == 42)
  }

  test("pushOnlyChar should not push the string if it is longer than one character.") {
    val stack = new PietStack()
    stack.pushOnlyChar("ab")
    assert(stack.isEmpty)
  }

  test("pushOnlyChar should push the char and convert it to integer.") {
    val stack = new PietStack()
    stack.pushOnlyChar("1")
    assert(stack.pop == 49)
  }

  test("pop should be ignored when the stack is empty.") {
    val stack = new PietStack()
    stack.pop
    assert(stack.isEmpty)
  }

  test("add should do nothing when one number on stack.") {
    val stack = new PietStack()
    stack.push(42)
    stack.add
    assert(stack.length == 1 && stack.pop == 42)
  }

  test("add should do nothing when no values on stack.") {
    val stack = new PietStack()
    stack.add
    assert(stack.isEmpty)
  }

  test("add should correctly add 2 numbers if stack.length >= 2.") {
    val stack = new PietStack()
    stack.push(42)
    stack.push(42)
    stack.add
    assert(stack.length == 1)
    assert(stack.pop == 84)
  }

  test("sub should do nothing when one number on stack.") {
    val stack = new PietStack()
    stack.push(42)
    stack.sub
    assert(stack.length == 1 && stack.pop == 42)
  }

  test("sub should do nothing when no values on stack.") {
    val stack = new PietStack()
    stack.sub
    assert(stack.isEmpty)
  }

  test("sub should correctly subtract 2 numbers if stack.length >= 2.") {
    val stack = new PietStack()
    stack.push(42)
    stack.push(12)
    stack.sub
    assert(stack.length == 1)
    assert(stack.pop == 30)
  }

  test("mul should do nothing when one number on stack.") {
    val stack = new PietStack()
    stack.push(42)
    stack.mul
    assert(stack.length == 1 && stack.pop == 42)
  }

  test("mul should do nothing when no values on stack.") {
    val stack = new PietStack()
    stack.mul
    assert(stack.isEmpty)
  }

  test("mul should correctly multiply 2 numbers if stack.length >= 2.") {
    val stack = new PietStack()
    stack.push(4)
    stack.push(17)
    stack.mul
    assert(stack.length == 1)
    assert(stack.pop == 68)
  }

  test("div should execute correctly when 2 or more values on stack."){
    val stack = new PietStack
    stack push 42
    stack push 5
    stack.div
    assertResult(8)(stack.pop)
  }

  test("div should do nothing when one value on stack.") {
    val stack = new PietStack
    stack push 42
    stack.div
    assertResult(42)(stack.pop)
    assert(stack.isEmpty)
  }

  test("div should do nothing when no values on stack.") {
    val stack = new PietStack
    stack.div
    assert(stack.isEmpty)
  }

  test("mod should execute correctly when 2 or more values on stack."){
    val stack = new PietStack
    stack push 42
    stack push 5
    stack.mod
    assertResult(2)(stack.pop)
  }

  test("mod should do nothing when one value on stack.") {
    val stack = new PietStack
    stack push 42
    stack.mod
    assertResult(42)(stack.pop)
    assert(stack.isEmpty)
  }

  test("mod should do nothing when no values on stack.") {
    val stack = new PietStack
    stack.mod
    assert(stack.isEmpty)
  }

  test("not should do nothing when no values on stack.") {
    val stack = new PietStack
    stack.not
    assert(stack.isEmpty)
  }

  test("not should push 0 if value on top of stack different than 0.") {
    val stack = new PietStack
    stack push -5
    stack.not
    assertResult(0)(stack.pop)
  }

  test("not should push 1 if value on top of stack is 0.") {
    val stack = new PietStack
    stack push 0
    stack.not
    assertResult(1)(stack.pop)
  }

  test("dup should do nothing if stack is empty.") {
    val stack = new PietStack
    stack.dup
    assert(stack.isEmpty)
  }

  test("dup should duplicate value on top of stack.") {
    val stack = new PietStack
    stack push 42
    stack.dup
    assertResult(42)(stack.pop)
    assertResult(42)(stack.pop)
  }

  test("greater should do nothing if one value on stack.") {
    val stack = new PietStack
    stack push 42
    stack.greater
    assertResult(1)(stack.length)
    assertResult(42)(stack.pop)
  }

  test("greater should do nothing if zero values on stack.") {
    val stack = new PietStack
    stack.greater
    assert(stack.isEmpty)
  }

  test("greater should pop 2 values and push a 1 if the second value from stack is greater than top value.") {
    val stack = new PietStack
    stack push 42
    stack push 14
    stack.greater
    assertResult(1)(stack.length)
    assertResult(1)(stack.pop)
  }

  test("greater should pop 2 values and push a 0 if the second value from stack is not greater than top value.") {
    val stack = new PietStack
    stack push -3
    stack push 14
    stack.greater
    assertResult(1)(stack.length)
    assertResult(0)(stack.pop)
  }

  test("roll <depth> values up <n> times, where <n> is the first value popped, and <depth> is the second.") {
    val stack = new PietStack
    stack.push(42)
    stack.push(10)
    stack.push(42)
    stack.push(0)
    stack.roll
    assert(stack.isEmpty)
  }
}