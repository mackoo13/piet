package image

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import org.scalatest._
import piet.Colors
import scala.Array._

class ImageLoaderTest extends FunSuite with Matchers {

  test("reload should throw InvalidImageDimensionsException if specified image and codel size don't fit.") {
    intercept[InvalidImageDimensionsException] {
      val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
      val imgLoader = new ImageLoader
      imgLoader.reload(imgPath, 2)
    }
  }

  test("rgbToPietCode should throw IllegalStateException if passed an invalid RGB code.") {
    intercept[IllegalStateException] {
      val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
      val imgLoader = new ImageLoader
      imgLoader.rgbToPietCode(0x000001)
    }
  }

  test("rgbToPietCode should return a correct piet color code for a piet-correct RGB code.") {
    val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
    val imgLoader = new ImageLoader
    assertResult(6)(imgLoader.rgbToPietCode(0xFFC0FFC0))
  }

  test("getRGBArray should return a table of colors of appropriate size for given codel size."){
    val imgPath = getClass.getClassLoader.getResource("2x3.png").toURI.getPath
    val imgLoader = new ImageLoader
    val assertedResultTable = ofDim[Int](3,2)
    val image = ImageIO.read(new File(imgPath))
    assertedResultTable(0) = Array(0xFFFFFFFF, 0xFF00FFFF)
    assertedResultTable(1) = Array(0xFFFF00FF, 0xFF0000FF)
    assertedResultTable(2) = Array(0xFFFF0000, 0xFF000000)

    assertedResultTable should equal (imgLoader.getRGBArray(image, 10))
  }

  test("getPietColorsArray should convert given RGB table correctly to piet colors array."){
    val rgbArray = ofDim[Int](2,3)
    rgbArray(0) = Array(0xFFFFFFFF, 0xFFFF00FF, 0xFFFF0000)
    rgbArray(1) = Array(0xFF00FFFF, 0xFF0000FF, 0xFF000000)
    val imgPath = getClass.getClassLoader.getResource("2x3.png").toURI.getPath
    val imgLoader = new ImageLoader

    val assertedResultTable = ofDim[Int](2,3)
    assertedResultTable(0) = Array(Colors.WHITE, 16, 1)
    assertedResultTable(1) = Array(10, 13, Colors.BLACK)

    assertedResultTable should equal (imgLoader.getPietColorsArray(rgbArray))
  }

  test("getPierColorsArray should throw IllegalStateException if a color in the given table is not piet-correct."){
    intercept[IllegalStateException]{
      val rgbArray = ofDim[Int](2,3)
      rgbArray(0) = Array(0xFFFFFFFF, 0xFFFF00FF, 0xFFFF0000)
      rgbArray(1) = Array(0xFF00FFFF, 0xFF009999, 0xFF000000)
      val imgPath = getClass.getClassLoader.getResource("2x3.png").toURI.getPath
      val imgLoader = new ImageLoader

      imgLoader.getPietColorsArray(rgbArray)
    }
  }

}