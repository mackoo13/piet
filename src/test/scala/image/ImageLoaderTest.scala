package image

import org.scalatest._
import scala.Array._

class ImageLoaderTest extends FunSuite with Matchers {

  test("ImageLoader should throw InvalidImageDimensionsException if specified image and codel size don't fit.") {
    intercept[InvalidImageDimensionsException] {
      val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
      val imgLoader = new ImageLoader(imgPath, 2)
    }
  }

  test("rgbToPietCode should throw IllegalStateException if passed an invalid RGB code.") {
    intercept[IllegalStateException] {
      val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
      val imgLoader = new ImageLoader(imgPath, 11)
      imgLoader.rgbToPietCode(0x000001)
    }
  }

  test("rgbToPietCode should return a correct piet color code for a piet-correct RGB code.") {
    val imgPath = getClass.getClassLoader.getResource("fibbig.png").toURI.getPath
    val imgLoader = new ImageLoader(imgPath, 11)
    assertResult(6)(imgLoader.rgbToPietCode(0xFFC0FFC0))
  }

  test("getRGBArray should return a table of colors of appropriate size for given codel size."){
    val imgPath = getClass.getClassLoader.getResource("2x3.png").toURI.getPath
    val imgLoader = new ImageLoader(imgPath, 10)

    val assertedResultTable = ofDim[Int](2,3)
    assertedResultTable(0) = Array(0xFFFFFFFF, 0xFFFF00FF, 0xFFFF0000)
    assertedResultTable(1) = Array(0xFF00FFFF, 0xFF0000FF, 0xFF000000)

    assertedResultTable should equal (imgLoader.getRGBArray())
  }

}