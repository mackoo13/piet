import scala.collection.mutable

class PietStack {

  //TODO wyjateczki

  var stack = new mutable.Stack[Int]  //czy to może być val...?

  def push(a:Int) = { stack.push(a) }

  def pushOnlyInt(input:String) = {
    try {
      push(input.toInt)
    } catch {
      case nfe:NumberFormatException => None
    }
  }

  def pushOnlyChar(input:String) = { if(input.length == 1) push(input(0).toInt) }

  def pop = { stack.pop() }

  def add = { push(pop+pop) }

  def sub = { push(-pop+pop) }

  def mul = { push(pop*pop) }

  def div = {
    val a:Int = pop
    val b:Int = pop
    if(a != 0) b/a else {push(b); push(a)}
  }

  def mod = {
    val a:Int = pop
    val b:Int = pop
    if(a != 0) push(b%a) else {push(b); push(a)}
  }

  def not = { push(if(pop == 0) 1 else 0) }

  def dup = { push(stack.head) }

  def greater = { if(pop<pop) 1 else 0 }

  def roll = {
    val rolls = pop
    val depth = pop
    if(depth>0 && depth<=stack.length) {
      val tempStack = stack.clone()
      for(i <- 0 until depth) pop
      for(i <- depth-1 to 0 by -1) {
        push(tempStack.apply((i+rolls)%depth))
      }
    } else {
      push(depth)
      push(rolls)
    }
  }

  override def toString = stack.addString(new StringBuilder(), "<br>").toString()

}
