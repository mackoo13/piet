package image

import org.scalatest.FunSuite

class ImageLoaderTest extends FunSuite {

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

}