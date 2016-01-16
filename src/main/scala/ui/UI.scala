package ui

import javax.swing.filechooser.FileNameExtensionFilter

import image.{InvalidImageDimensionsException, ImageLoader}
import piet.PietProgram

import java.io.File
import scala.Array._
import scala.swing.FileChooser.Result
import scala.swing._

class UI extends MainFrame {

  var codelsArray = ofDim[Int](8, 8)  //kolumny sa zamienione z wierszami, ale to nic zlego, bo to jest tylko do testowania
  codelsArray(0) = Array(0, -1, 13, 13, 0, 0, 0, 0)
  codelsArray(1) = Array(14, 0, 0, 1, 0, 0, 0, 0)
  codelsArray(2) = Array(-1, 10, 10, 1, 0, 0, 10, 13)
  codelsArray(3) = Array(-1, 1, 1, 1, 0, 0, 10, 0)
  codelsArray(4) = Array(0, 5, 4, 0, 0, 0, 10, 2)
  codelsArray(5) = Array(15, 2, 2, 0, 0, 0, 10, 1)
  codelsArray(6) = Array(-1, 11, 7, 0, 10, 0, 1, 1)
  codelsArray(7) = Array(0, 0, 17, -1, 0, 16, 0, 1)

  val inputField = new TextField { columns = 1 }
  val codelSizeField = new TextField { columns = 2; text="1" }
  codelSizeField.horizontalAlignment_=(Alignment.Right)

  val codels = new Codels(codelsArray)
  val program = new PietProgram(this, codelsArray)
  val imageLoader = new ImageLoader

  val labelDP = new Label("DP: %s".format(program.nav.dp.name))
  val labelCC = new Label("CC: %s".format(program.nav.cc.name))
  val labelCurrentCoords = new Label("COORDS: %s".format(program.nav.currentCodel))
  val labelNextCoords = new Label("NEXT: %s".format(program.nav.next()))
  val labelNextOp = new Label("OP: %s".format(program.opName(program.nav.getColor(program.nav.currentCodel), program.nav.getColor(program.nav.next()))))
  val labelStack = new Label("<html>STACK:</html>")
  val labelOut = new Label("<html>OUT:</html>")

  var startDir: File = new FileChooser().selectedFile

  preferredSize = new Dimension(620, 500)
  title = "Piet"
  codels.setNextCodel(program.nav.next())

  def codelSize = codelSizeField.text

  def loadFile() = try {
    val chooser = new FileChooser(startDir)
    chooser.title_=("Select image")
    chooser.fileFilter_=(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "bmp", "gif", "png"))
    val result: Result.Value = chooser.showDialog(codels, "Load")
    if (result == Result.Approve) {
      val filePath = chooser.selectedFile.toString
      startDir = chooser.selectedFile.getParentFile
      val codelsArray = imageLoader.reload(filePath, codelSize.toInt)
      codels.reload(codelsArray)
      program.reload(codelsArray)

      codels.repaint()
      codels.setNextCodel(program.nav.next())
    }
  } catch {
    case _: InvalidImageDimensionsException => None      //TODO make a popup saying shit happens, add more cases
  }

  def step() = {

    codels.setCurrentCodel(program.nav.lastInBlock(program.nav.currentCodel))
    codels.setNextCodel(program.nav.next())
    codels.repaint()
    labelDP.text = "DP: %s".format(program.nav.dp.name)
    labelCC.text = "CC: %s".format(program.nav.cc.name)
    labelCurrentCoords.text = "COORDS: %s".format(program.nav.currentCodel)
    labelNextCoords.text = "NEXT: %s".format(program.nav.next())
    labelNextOp.text = "OP: %s".format(program.opName(program.nav.getColor(program.nav.currentCodel), program.nav.getColor(program.nav.next())))
    labelStack.text = "<html>STACK:<br>%s</html>".format(program.stack)
    labelOut.text = "<html>OUT:<br>%s</html>".format(program.out)

    program.step()
  }

    contents = new GridBagPanel {
    def constraints(x: Int, y: Int,
                    gridwidth: Int = 1, gridheight: Int = 1,
                    weightx: Double = 0.0, weighty: Double = 0.0,
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
    :Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c
    }

    add(Button("Load file") {loadFile()},constraints(0, 0))
    add(codelSizeField, constraints(0, 1))
    add(Button("step") {step()},constraints(0, 2))
    add(labelDP, constraints(0, 3))
    add(labelCC, constraints(0, 4))
    add(labelCurrentCoords, constraints(0, 5))
    add(labelNextCoords, constraints(0, 6))
    add(labelNextOp, constraints(0, 7))
    add(labelStack, constraints(0, 8, fill=GridBagPanel.Fill.Vertical))
    add(codels, constraints(1, 0, gridheight=9, weightx = 1.0, weighty=1.0))
    add(inputField,
      constraints(2, 0, weightx=1.0, fill=GridBagPanel.Fill.Horizontal))
    add(labelOut, constraints(2, 1, gridheight=8, fill=GridBagPanel.Fill.Vertical))
  }

}

object PietInterpreter {
  def main(args: Array[String]) {
    val ui = new UI
    ui.visible = true
  }
}