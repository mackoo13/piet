import java.awt.{BasicStroke, Color}
import scala.swing._

class Codels(val width:Int, val height:Int, val codelsArray:Array[Array[Int]]) extends Panel {

  preferredSize = new Dimension(300, 300)
  var componentSize = new Dimension(0, 0)
  var currentX = 0
  var currentY = 0

  def updateCurrentCodel(x:Int, y:Int) = {
    currentX = x
    currentY = y
  }

  override def paintComponent(g : Graphics2D) {
    val d = size
    val codelWidth = d.width/width
    val codelHeight = d.height/height
    println("szerokosc: "+d.width+" wysokosc: "+d.height)
    g.setColor(Color.white)
    g.fillRect(0,0, d.width, d.height)
    val rowWid = d.height/height
    val colWid = d.width/width
    val wid = rowWid min colWid

    val x0 = (d.width - width * wid)/2
    val y0 = (d.height - height * wid)/2
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        g.setColor(new Color(codelsArray(x)(y)/6*80, codelsArray(x)(y)%6*51, 99))
        g.fillRect(x0 + x * wid, y0 + y * wid, wid, wid)
      }
    }

    val currentCodel = new Rectangle(currentX*codelWidth, currentY*codelHeight, codelWidth, codelHeight)
    g.setStroke(new BasicStroke(4))
    g.setColor(Color.white)
    g.draw(currentCodel)
    g.setStroke(new BasicStroke(2))
    g.setColor(Color.black)
    g.draw(currentCodel)
  }
}
