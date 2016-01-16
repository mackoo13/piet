package piet

import ui.UI

class PietProgram(val ui:UI, val codelsArray:Array[Array[Int]]) {

  val nav = new PietNavigator(codelsArray)
  val stack = new PietStack
  val out = new PietOutput
  var waiting = false

  def execOp(from:Int, to:Int) = {
    val hueChange = ((to/3)-(from/3)+66)%6
    val lightnessChange = (to-from+33)%3

    (hueChange, lightnessChange) match {
      case (0, 0) => None     //nic
      case (0, 1) => stack.push(nav.blockArea(nav.currentCodel))
      case (0, 2) => stack.pop
      case (1, 0) => stack.add
      case (1, 1) => stack.sub
      case (1, 2) => stack.mul
      case (2, 0) => stack.div
      case (2, 1) => stack.mod
      case (2, 2) => stack.not
      case (3, 0) => stack.greater
      case (3, 1) => nav.pointer(stack.pop)
      case (3, 2) => nav.switch(stack.pop)
      case (4, 0) => stack.dup
      case (4, 1) => stack.roll
      case (4, 2) => if(ui.inputField.text=="") waiting=true else { waiting=false; stack.pushOnlyInt(ui.inputField.text)}
      case (5, 0) => if(ui.inputField.text=="") waiting=true else { waiting=false; stack.pushOnlyChar(ui.inputField.text)}
      case (5, 1) => out.intOut(stack.pop)
      case (5, 2) => out.charOut(stack.pop)
    }
  }

  def opName(from:Int, to:Int) = {

    val hueChange = ((to/3)-(from/3)+66)%6
    val lightnessChange = (to-from+33)%3

    if(to<0 || nav.noOp) "NONE" else (hueChange, lightnessChange) match {
      case (0, 0) => "NONE"
      case (0, 1) => "PUSH"
      case (0, 2) => "POP"
      case (1, 0) => "ADD"
      case (1, 1) => "SUB"
      case (1, 2) => "MUL"
      case (2, 0) => "DIV"
      case (2, 1) => "MOD"
      case (2, 2) => "NOT"
      case (3, 0) => "GREATER"
      case (3, 1) => "POINTER"
      case (3, 2) => "SWITCH"
      case (4, 0) => "DUP"
      case (4, 1) => "ROLL"
      case (4, 2) => "IN INT"
      case (5, 0) => "IN CHAR"
      case (5, 1) => "OUT INT"
      case (5, 2) => "OUT CHAR"
    }
  }

  def step() = {
    val nextCodel = nav.next()
    if(nav.getColor(nextCodel) == Colors.BLACK) nav.changeDirection()
    else nav.moveFailures = 0
    if(nav.moveFailures == 0 && !nav.noOp) execOp(nav.getColor(nav.currentCodel), nav.getColor(nextCodel))
    if(nav.moveFailures == 0 && !waiting) nav.currentCodel = nextCodel
  }

}
