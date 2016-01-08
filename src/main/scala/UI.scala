import scala.swing._
import javax.swing.ImageIcon
import Array._

class UI extends MainFrame {

  var codelsArray = ofDim[Int](4, 4)
  for (i <- 0 to 3) {
    for (j <- 0 to 3) {
      codelsArray(i)(j) = j + i + 1
    }
  }

  val navigator = new PietNavigator(codelsArray)
  val codels = new Codels(4, 4, navigator.codelsArray)
  var i = 0

  title = "GUI Program #1"
  val l1 = new Label("bla %d".format(i))
  preferredSize = new Dimension(320, 240)
  contents = new GridPanel(3, 2) {
    contents += new Label {
      icon = new ImageIcon("src/main/student.png")
    }
    contents += Button("A Button") {i+=1; l1.text = "ble %d".format(i)}
    contents += codels
    contents += l1
    contents += new CheckBox("Check me!")
    contents += Button("Close") { sys.exit(0) }
  }
}

object GuiProgramOne {
  def main(args: Array[String]) {
    val ui = new UI
    ui.visible = true
    println("End of main function")
  }
}