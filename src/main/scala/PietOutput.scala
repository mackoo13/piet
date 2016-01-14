import scala.collection.mutable.ListBuffer

class PietOutput {

  var out = new ListBuffer[String]

  def charOut(c:Int) = { out+=c.toChar.toString }

  def intOut(c:Int) = { out+=c.toString }

  override def toString = out.addString(new StringBuilder(), "<br>").toString()

}
