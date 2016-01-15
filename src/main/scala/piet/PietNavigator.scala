package piet

import utils.Point

class PietNavigator (val codelsArray:Array[Array[Int]]) {

  val width = codelsArray.length
  val height = codelsArray(0).length

  var x:Int = 0
  var y:Int = 0
  var dp:DP = DPRIGHT
  var cc:CC = CCLEFT
  var currentCodel:Point = new Point(0, 0)
  var currentBlockArray: Array[Array[Boolean]] = Array.ofDim[Boolean](width, height)

  def pointer(n:Int) = {
    for(i <- 0 until n%4) dp = dp.next
  }

  def switch(n:Int) = {
    if(n%2 == 1) cc = cc.next
  }

//TODO -2 z braku lepszego pomyslu, raczej wywolanie dla nulla nie ma sensu
  def getColor(p:Point):Int = if(p==null) -2 else if(p.x>=0 && p.y>=0 && p.x<width && p.y<height) codelsArray(p.x)(p.y) else Colors.BLACK
                                        // ^ czy to -2 to też ma być BLACK?
  def next(): Point = {
    val i = 0
    var res:Point = null
    while(i<4 && res == null) {
      if(getColor(lastInBlock(currentCodel)+dp.step) == Colors.BLACK) cc = cc.next
      else res = lastInBlock(currentCodel)            // v czy to też ma być BLACK?
      if(getColor(lastInBlock(currentCodel)+dp.step) == -2 && res == null) dp = dp.next
      else if(res == null) res = lastInBlock(currentCodel)
    }
    if(res == null) return null     //TODO tu trzeba rzucic wyjatkiem moze, to znaczy ze koniec programu
    do { res += dp.step } while(getColor(res) == Colors.WHITE)
    if(getColor(res) == Colors.BLACK) return null
    res
  }

  def buildBlockArray(p:Point) = {
    val blockColor = codelsArray(p.x)(p.y)

    def expandFrom(x:Int, y:Int): Unit = {
      if(!currentBlockArray(x)(y)) {
        currentBlockArray(x)(y) = true
        if (x > 0 && codelsArray(x - 1)(y) == blockColor) expandFrom(x - 1, y)
        if (x < width - 1 && codelsArray(x + 1)(y) == blockColor) expandFrom(x + 1, y)
        if (y > 0 && codelsArray(x)(y - 1) == blockColor) expandFrom(x, y - 1)
        if (y < height - 1 && codelsArray(x)(y + 1) == blockColor) expandFrom(x, y + 1)
      }
    }

    currentBlockArray = Array.fill[Boolean](width, height)(false)
    expandFrom(p.x, p.y)
  }

  def lastInBlock(p:Point): Point = {
    //TODO jak ArrayOutOfBounds, to wszystko false czyli zle
    buildBlockArray(p)
    val dp1 = if(cc==CCLEFT) dp.next else dp.prev
    val dp2 = dp.next.next
    var ix = if(dp1 == DPRIGHT || dp2 == DPRIGHT) 0 else width-1
    var iy = if(dp1 == DPDOWN || dp2 == DPDOWN) 0 else height-1
    while(!currentBlockArray(ix)(iy)) {
      ix += dp1.step.x
      iy += dp1.step.y
      if(ix < 0) { ix=width-1; iy+=dp2.step.y }
      else if(iy < 0) { iy=height-1; ix+=dp2.step.x }
      else if(ix > width-1) { ix=0; iy+=dp2.step.y }
      else if(iy > height-1) { iy=0; ix+=dp2.step.x }
    }
    new Point(ix, iy)
  }

  def blockArea(p:Point): Int = {
    buildBlockArray(p)
    currentBlockArray.map(r => r.count(_ == true)).sum
  }

}



sealed abstract class DP { val step:Point; val next:DP; val prev:DP; val name:String }
case object DPRIGHT extends DP { val step = new Point(1, 0); val next = DPDOWN; val prev = DPUP; val name = "RIGHT" }
case object DPDOWN extends DP { val step = new Point(0, 1); val next = DPLEFT; val prev = DPRIGHT; val name = "DOWN" }
case object DPLEFT extends DP { val step = new Point(-1, 0); val next = DPUP; val prev = DPDOWN; val name = "LEFT" }
case object DPUP extends DP { val step = new Point(0, -1); val next = DPRIGHT; val prev = DPLEFT; val name = "UP" }

sealed abstract class CC { val next:CC; val name:String }
case object CCLEFT extends CC { val next = CCRIGHT; val name = "LEFT" }
case object CCRIGHT extends CC { val next = CCLEFT; val name = "RIGHT" }
