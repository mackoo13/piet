package image

import java.io.File
import javax.imageio.ImageIO
import piet.Colors

import scala.Array._

class ImageLoader(val imagePath: String, val codelSize: Int) {

  val image = ImageIO.read(new File(imagePath))
  val height = image.getHeight
  val width = image.getWidth
  if(height % codelSize != 0 || width % codelSize != 0)
    throw new InvalidImageDimensionsException("Specified codel size doesn't fit image dimensions")

  def reload(imagePath: String, codelSize: Int) = {
    val this.imagePath = imagePath
    val this.image = ImageIO.read(new File(imagePath))
    val this.width = image.getWidth
    if(height % codelSize != 0 || width % codelSize != 0)
      throw new InvalidImageDimensionsException("Specified codel size doesn't fit image dimensions")
  }


  def getRGBArray: Array[Array[Int]] = {

    val outputWidth = width/codelSize
    val outputHeight = height/codelSize
    val pixelArray = ofDim[Int](outputWidth,outputHeight)

    for (i <- 0 until outputWidth)
      for (j <- 0 until outputHeight)
        pixelArray(i)(j) = image.getRGB(i*codelSize, j*codelSize)

    pixelArray
  }

  def getPietColorsArray(rgbArray: Array[Array[Int]]):Array[Array[Int]] = rgbArray map (_ map rgbToPietCode)

  def rgbToPietCode(pixelColor: Int): Int = pixelColor match {
    // All colours as we know them prefixed by FF 'cause the pixed we get is coded on 4 bytes,
    // First one meaning alpha and is FF
    case 0xFFFFC0C0 => 0  // light red
    case 0xFFFF0000 => 1  // red
    case 0xFFC00000 => 2  // dark red
    case 0xFFFFFFC0 => 3  // light yellow
    case 0xFFFFFF00 => 4  // yellow
    case 0xFFC0C000 => 5  // dark yellow
    case 0xFFC0FFC0 => 6  // light green
    case 0xFF00FF00 => 7  // green
    case 0xFF00C000 => 8  // dark green
    case 0xFFC0FFFF => 9  // light cyan
    case 0xFF00FFFF => 10 // cyan
    case 0xFF00C0C0 => 11 // dark cyan
    case 0xFFC0C0FF => 12 // light blue
    case 0xFF0000FF => 13 // blue
    case 0xFF0000C0 => 14 // dark blue
    case 0xFFFFC0FF => 15 // light magenta
    case 0xFFFF00FF => 16 // magenta
    case 0xFFC000C0 => 17 // dark magenta
    case 0xFFFFFFFF => Colors.WHITE  // white
    case 0xFF000000 => Colors.BLACK  // black
    case _ => throw new IllegalStateException("Encountered unrecognizable color in the loaded image.")
  }
}

class InvalidImageDimensionsException(message: String) extends IllegalArgumentException(message){}
