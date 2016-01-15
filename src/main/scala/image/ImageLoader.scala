package image

import java.io.File
import javax.imageio.ImageIO

import scala.Array._

class ImageLoader(imagePath: String, codelSize: Int) {

  val image = ImageIO.read(new File(imagePath))
  val height = image.getHeight
  val width = image.getWidth
  if(height % codelSize != 0 || width % codelSize != 0)
    throw new InvalidImageDimensionsException("Specified codel size doesn't fit image dimensions")


  def rgbArray(): Array[Array[Int]] = {

    val outputWidth = width/codelSize
    val outputHeight = height/codelSize
    val pixelArray = ofDim[Int](outputWidth, outputHeight)

    for (i <- 0 until outputHeight)
      for (j <- 0 until outputWidth)
        pixelArray(i)(j) = image.getRGB(i*codelSize, j*codelSize)

    pixelArray
  }

  def rgbToPietCode(pixelColor: Int): Int = pixelColor match {
    case 0xFFC0C0 => 0  // light red
    case 0xFF0000 => 1  // red
    case 0xC00000 => 2  // dark red
    case 0xFFFFC0 => 3  // light yellow
    case 0xFFFF00 => 4  // yellow
    case 0xC0C000 => 5  // dark yellow
    case 0xC0FFC0 => 6  // light green
    case 0x00FF00 => 7  // green
    case 0x00C000 => 8  // dark green
    case 0xC0FFFF => 9  // light cyan
    case 0x00FFFF => 10 // cyan
    case 0x00C0C0 => 11 // dark cyan
    case 0xC0C0FF => 12 // light blue
    case 0x0000FF => 13 // blue
    case 0x0000C0 => 14 // dark blue
    case 0xFFC0FF => 15 // light magenta
    case 0xFF00FF => 16 // magenta
    case 0xC000C0 => 17 // dark magenta
    case 0xFFFFFF => -1  // white
    case 0x000000 => -2  // black
    case _ => throw new IllegalStateException("Encountered unrecognizable color in the loaded image.")
  }
}

class InvalidImageDimensionsException(message: String) extends IllegalArgumentException(message){}
