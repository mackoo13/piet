package piet

import scala.collection.mutable

class PietStack {

  val stack = new mutable.Stack[Int]

  def reload = { val this.stack = new mutable.Stack[Int] }

  def push(a:Int) = { stack.push(a) }

  def isEmpty = stack.isEmpty

  def length = stack.length

  def pushOnlyInt(input:String) = {
    try {
      push(input.toInt)
    } catch {
      case nfe:NumberFormatException => None
    }
  }

  def pushOnlyChar(input:String) = { if(input.length == 1) push(input(0).toInt) }

  def pop = if(!isEmpty) stack.pop()

  def popOrZero = if(!isEmpty) stack.pop() else 0 //ZUOOOOO

  def operation1(fun: Int => Unit) = {
    try {
      val a = pop.asInstanceOf[Int]
      fun(a)
    } catch {
      case _: Throwable => None
    }
  }

  def operation2(fun: (Int, Int) => Unit) = {
    try {
      val a = pop.asInstanceOf[Int]
      try {
        val b = pop.asInstanceOf[Int]
        fun(a, b)
      } catch {
        case _: Throwable => push(a)
      }
    } catch {
      case _: Throwable => None
    }
  }

  def add = operation2((a:Int, b:Int)=> push(a+b))

  def sub = operation2((a:Int, b:Int)=> push(b-a))

  def mul = operation2((a:Int, b:Int)=> push(b*a))

  def div = operation2((a:Int, b:Int)=> if(a!=0) push(b/a) else {push(b); push(a)})

  def mod = operation2((a:Int, b:Int)=> if(a != 0) push(b%a) else {push(b); push(a)})

  def not = operation1((a:Int) => push(if(a == 0) 1 else 0))

  def dup = { if(!isEmpty) push(stack.head) }

  def greater = operation2((a:Int, b:Int) => push(if(b>a) 1 else 0))

  def roll = {
    if(stack.length>=2) {
      try {
        val rolls = pop.asInstanceOf[Int]
        try {
          val depth = pop.asInstanceOf[Int] min stack.length
          if (depth > 0) {
            val tempStack = stack.clone()
            for (i <- 0 until depth) pop
            for (i <- depth-1 to 0 by -1) {
              var j = (i + rolls) % depth
              if(j<0) j+=depth
              push(tempStack.apply(j))
            }
          } else {
            push(depth)
            push(rolls)
          }
        } catch {
          case _: Throwable => push(rolls)
        }
      } catch {
        case _: Throwable => None
      }
    }
  }

  override def toString = stack.addString(new StringBuilder(), "<br>").toString()

}
