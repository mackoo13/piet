class Point (val x:Int, val y:Int) {

  def +(p:Point):Point = new Point(x+p.x, y+p.y)

  override def toString = "(" + x + ", " + y + ")"

}
