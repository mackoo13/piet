package piet

import scala.collection.mutable.ListBuffer

class PietOutput {

  var out = new ListBuffer[String]

  def reload() = {out = new ListBuffer[String]}

  def charOut(c:AnyVal) = {
    try {
      out+=c.asInstanceOf[Int].toChar.toString
    } catch {
      case _: Throwable => None
    }
  }

  def intOut(c:AnyVal) = {
    try {
      out+=c.asInstanceOf[Int].toString
    } catch {
      case _: Throwable => None
    }
  }

  override def toString = out.addString(new StringBuilder(), "<br>").toString()

}
