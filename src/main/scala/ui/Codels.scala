package ui

import java.awt.{BasicStroke, Color}

import piet.Colors

import scala.swing._
import utils.Point

class Codels(var codelsArray:Array[Array[Int]]) extends Panel {

  def width = codelsArray.length
  def height = codelsArray(0).length

  val dim = new Dimension(450, 450)
  preferredSize = dim
  minimumSize = dim
  maximumSize = dim
  var currentX = 0
  var currentY = 0
  var nextX = 0
  var nextY = 0

  def reload(codelsArray:Array[Array[Int]]) = {
    this.codelsArray = codelsArray
    currentX = 0
    currentY = 0
    nextX = 0
    nextY = 0
  }

  def setNextCodel(p:Point) = {
    nextX = p.x
    nextY = p.y
  }
  
  def setCurrentCodel(p:Point) = {
    currentX = p.x
    currentY = p.y
  }

  override def paintComponent(g : Graphics2D) {
    val wid = size.width/(width+1) min size.height/(height+1)
    
    g.setColor(Color.black)
    g.fillRect(0,0, size.width, size.height)

    val x0 = (size.width - width*wid)/2
    val y0 = (size.height - height*wid)/2
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        g.setColor(new Color(PietCodeToRGB(codelsArray(x)(y))))
        g.fillRect(x0+x*wid, y0+y*wid, wid, wid)
      }
    }

    val currentCodel = new Rectangle(x0+currentX*wid, y0+currentY*wid, wid, wid)
    val nextCodel = new Rectangle(x0+nextX*wid, y0+nextY*wid, wid, wid)

    g.setStroke(new BasicStroke(4))
    g.setColor(Color.white)
    g.draw(currentCodel)
    g.setColor(Color.black)
    g.draw(nextCodel)
    g.setStroke(new BasicStroke(2))
    g.setColor(Color.black)
    g.draw(currentCodel)
    g.setColor(Color.white)
    g.draw(nextCodel)
  }

  def PietCodeToRGB(pietCode: Int): Int = pietCode match {
    case 0 => 0xFFC0C0  // light red
    case 1 => 0xFF0000  // red
    case 2 => 0xC00000  // dark red
    case 3 => 0xFFFFC0  // light yellow
    case 4 => 0xFFFF00  // yellow
    case 5 => 0xC0C000  // dark yellow
    case 6 => 0xC0FFC0  // light green
    case 7 => 0x00FF00  // green
    case 8 => 0x00C000  // dark green
    case 9 => 0xC0FFFF  // light cyan
    case 10 => 0x00FFFF // cyan
    case 11 => 0x00C0C0 // dark cyan
    case 12 => 0xC0C0FF // light blue
    case 13 => 0x0000FF // blue
    case 14 => 0x0000C0 // dark blue
    case 15 => 0xFFC0FF // light magenta
    case 16 => 0xFF00FF // magenta
    case 17 => 0xC000C0 // dark magenta
    case Colors.WHITE => 0xFFFFFF  // white
    case Colors.BLACK => 0x000000  // black
    case _ => throw new IllegalStateException("Encountered unrecognizable color in the loaded image.")
  }
}
