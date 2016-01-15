package piet

import ui.UI

import scala.Array._

class PietProgram(val ui:UI) {

  var codelsArray = ofDim[Int](4, 4)
  codelsArray(0) = Array(0, 1, 9, 0)
  codelsArray(1) = Array(0, 2, 0, 10)
  codelsArray(2) = Array(1, 0, 0, 3)
  codelsArray(3) = Array(1, 1, 2, 5)

  val nav = new PietNavigator(codelsArray)
  val stack = new PietStack
  val out = new PietOutput

  var stopped = false

  def execOp(from:Int, to:Int) = {
    val hueChange = ((to/3)-(from/3)+66)%6
    val lightnessChange = (to-from+33)%3

//    println(hueChange + "*" + lightnessChange)
//    @up testy sobie napisz a nie printować będzie :D
    //komu by sie chcialo :p no wiem ze trzeba...

    (hueChange, lightnessChange) match {
      case (0, 0) => None     //nic
      case (0, 1) => stack.push(nav.blockArea(nav.currentCodel))
      case (0, 2) => None//stack.pop
      case (1, 0) => stack.add
      case (1, 1) => None//stack.sub
      case (1, 2) => None//stack.mul
      case (2, 0) => None//stack.div
      case (2, 1) => None//stack.mod
      case (2, 2) => None//stack.not
      case (3, 0) => None//stack.greater
      case (3, 1) => None//nav.pointer(stack.pop)
      case (3, 2) => None//nav.switch(stack.pop)
      case (4, 0) => None//stack.dup
      case (4, 1) => None//stack.roll
      case (4, 2) => None//if(ui.inputField.text=="") stopped=true else stack.pushOnlyInt(ui.inputField.text)
      case (5, 0) => None//if(ui.inputField.text=="") stopped=true else stack.pushOnlyChar(ui.inputField.text)
      case (5, 1) => None//out.intOut(stack.pop)
      case (5, 2) => None //out.charOut(stack.pop)
    }

  }

  def step() = {
    val nextCodel = nav.next()
    println(nav.currentCodel + " to " + nextCodel)

    execOp(nav.getColor(nav.currentCodel), nav.getColor(nextCodel))
    if(!stopped) nav.currentCodel = nextCodel
  }

}
