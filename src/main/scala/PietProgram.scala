import Array._

class PietProgram (val codelsArray:Array[Array[Int]]) {

  val width = codelsArray.length
  val height = codelsArray(0).length
  var dp:DP = DPRIGHT
  var cc:CC = CCLEFT
  var current:Point = new Point(0, 0)
  var currentBlockArray: Array[Array[Boolean]] = Array.ofDim[Boolean](width, height)

  def next(p:Point): Point = {
    new Point(p.x + dp.x, p.y + dp.y)
  }

  def buildBlockArray(p:Point) = {
    val blockColor = codelsArray(p.x)(p.y)

    def expandFrom(x:Int, y:Int): Unit = {
      if(!currentBlockArray(x)(y)) {
        currentBlockArray(x)(y) = true
        if (x > 0 && codelsArray(x - 1)(y) == blockColor) expandFrom(x - 1, y)
        if (x < width - 1 && codelsArray(x + 1)(y) == blockColor) expandFrom(x + 1, y)
        if (y > 0 && codelsArray(x)(y - 1) == blockColor) expandFrom(x, y - 1)
        if (y < width - 1 && codelsArray(x)(y + 1) == blockColor) expandFrom(x, y + 1)
      }
    }

    currentBlockArray = Array.fill[Boolean](width, height)(false)
    expandFrom(p.x, p.y)
  }

  def lastInBlock(p:Point): Point = {
    new Point(p.x, p.y)
  }

  def blockArea(p:Point): Int = {
    currentBlockArray.map(r => r.count(_ == true)).sum
  }

  println(current)
  current = next(current)
  println(current)

  println(dp.x)
  dp = dp.next
  println(dp.x)

  buildBlockArray(new Point(0, 3))
  println(currentBlockArray.deep.mkString("\n"))
  println(blockArea(new Point(0, 3)))

}

sealed trait DP { val x:Int; val y:Int; val next:DP; val prev:DP }
case object DPRIGHT extends DP { val x = 1; val y = 0; val next = DPDOWN; val prev = DPUP }
case object DPDOWN extends DP { val x = 0; val y = 1; val next = DPLEFT; val prev = DPRIGHT }
case object DPLEFT extends DP { val x = -1; val y = 0; val next = DPUP; val prev = DPDOWN }
case object DPUP extends DP { val x = 0; val y = -1; val next = DPRIGHT; val prev = DPLEFT }

sealed trait CC { val next:CC }
case object CCLEFT extends CC { val next = CCRIGHT }
case object CCRIGHT extends CC { val next = CCLEFT }
