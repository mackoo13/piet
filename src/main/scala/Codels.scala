import java.awt.Color
import scala.swing._

class Codels(val width:Int, val height:Int, val codelsArray:Array[Array[Int]]) extends Component {
  println("codels")

  override def paintComponent(g : Graphics2D) {
    val d = size
    g.setColor(Color.white)
    g.fillRect(0,0, d.width, d.height)
    val rowWid = d.height/height
    val colWid = d.width/width
    val wid = rowWid min colWid

    val x0 = (d.width - width * wid)/2
    val y0 = (d.height - height * wid)/2
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        if(codelsArray(x)(y)==1)
          g.setColor(Color.orange)
        else
          g.setColor(Color.blue)
        g.fillRect(x0 + x * wid, y0 + y * wid, wid, wid)
      }
    }
  }
}