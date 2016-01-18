package ui

import javax.swing.filechooser.FileNameExtensionFilter

import image.{InvalidImageDimensionsException, ImageLoader}
import piet.PietProgram

import java.io.{IOException, File}
import scala.Array._
import scala.swing.FileChooser.Result
import scala.swing._

class UI extends MainFrame {


  //FIELDS INITIALIZATION
  var codelsArray = Array(Array(-2))
  var finished = false
  val codels = new Codels(codelsArray)
  val program = new PietProgram(this, codelsArray)
  val imageLoader = new ImageLoader
  var startDir: File = new FileChooser().selectedFile

  val inputField = new TextField { columns = 1 }
  val numberOfStepsField = new TextField { columns = 2; text="1" }
  val codelSizeField = new TextField { columns = 2; text="1" }
  numberOfStepsField.horizontalAlignment_=(Alignment.Right)
  codelSizeField.horizontalAlignment_=(Alignment.Right)


  //GUI PROPERTIES
  preferredSize = new Dimension(800, 600)
  resizable = false
  title = "Piet Interpreter"


  //GUI COMPONENTS
  val labelDP = new Label("DP: %s".format(program.nav.dp.name))
  val labelCC = new Label("CC: %s".format(program.nav.cc.name))
  val labelCurrentCoords = new Label("COORDS: %s".format(program.nav.currentCodel))
  val labelNextCoords = new Label("NEXT: %s".format(program.nav.next()))
  val labelNextOp = new Label("OP: %s".format(program.opName(program.nav.getColor(program.nav.currentCodel), program.nav.getColor(program.nav.next()))))
  val labelStack = new Label("<html>STACK:</html>") {minimumSize=new Dimension(100, 400)}
  val labelOut = new Label("<html>OUT:</html>") {minimumSize=new Dimension(650, 100)}
  labelStack.verticalAlignment_=(Alignment.Bottom)
  labelOut.verticalAlignment_=(Alignment.Top)

  val stepButton = Button("Step") {step}
  val multistepButton = Button("Multiple steps") {multipleSteps(numberOfStepsField.text)}
  stepButton.enabled_=(false)
  multistepButton.enabled_=(false)



  def codelSize = codelSizeField.text

  def endProgram() = {
    if(!finished) Dialog.showMessage(codels, "Program finished.", title="Info")
    stepButton.enabled_=(false)
    multistepButton.enabled_=(false)
    finished = true
  }

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

      stepButton.enabled_=(true)
      multistepButton.enabled_=(true)

      codels.setNextCodel(program.nav.next())
      codels.repaint()
      updateLabels
    }
    finished = false
  } catch {
    case e: IOException => Dialog.showMessage(codels, "An error occured when trying to open the file.", title="Loading error")
    case e: InvalidImageDimensionsException => Dialog.showMessage(codels, e.getMessage, title="Loading error")
    case e: IllegalStateException => Dialog.showMessage(codels, e.getMessage, title="Loading error")
    case e: NumberFormatException => Dialog.showMessage(codels, "You must specify the codel size first!", title="Loading error")
    case e: IllegalArgumentException => Dialog.showMessage(codels, e.getMessage, title="Loading error")
  }

  def updateLabels() = {
    labelDP.text = "DP: %s".format(program.nav.dp.name)
    labelCC.text = "CC: %s".format(program.nav.cc.name)
    labelCurrentCoords.text = "COORDS: %s".format(program.nav.currentCodel)
    labelNextCoords.text = "NEXT: %s".format(program.nav.next())
    labelNextOp.text = "OP: %s".format(program.opName(program.nav.getColor(program.nav.currentCodel), program.nav.getColor(program.nav.next())))
    labelStack.text = "<html>%s<br><br>STACK</html>".format(program.stack)
    labelOut.text = "<html>OUT:<br>%s</html>".format(program.out)
  }

  def step = {
    program.step()
    codels.setCurrentCodel(program.nav.lastInBlock(program.nav.currentCodel))
    codels.setNextCodel(program.nav.next())
    codels.repaint()
    updateLabels
  }

  def multipleSteps(nText:String) = {
    try {
      val n = nText.toInt
      if(n>0) {
        for(i <- 0 until n) {
          step
        }
      }
    } catch {
      case _: Throwable => None
    }
  }


  //GUI LAYOUT
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

    add(Button("Load file") {loadFile()},constraints(0, 0, fill=GridBagPanel.Fill.Horizontal))
    add(codelSizeField, constraints(0, 1, fill=GridBagPanel.Fill.Horizontal))
    add(stepButton,constraints(0, 2, fill=GridBagPanel.Fill.Horizontal))
    add(multistepButton,constraints(0, 3, fill=GridBagPanel.Fill.Horizontal))
    add(numberOfStepsField, constraints(0, 4, fill=GridBagPanel.Fill.Horizontal))
    add(labelDP, constraints(0, 5))
    add(labelCC, constraints(0, 6))
    add(labelCurrentCoords, constraints(0, 7))
    add(labelNextCoords, constraints(0, 8))
    add(labelNextOp, constraints(0, 9))
    add(inputField,
        constraints(0, 10, fill=GridBagPanel.Fill.Horizontal))
    add(codels, constraints(1, 0, gridheight=11))
    add(new ScrollPane(labelStack) {val dim=new Dimension(100, 450); preferredSize=dim; maximumSize=dim; minimumSize=dim},
      constraints(2, 0, gridheight=11))
    add(new ScrollPane(labelOut) {val dim=new Dimension(650, 100); preferredSize=dim; maximumSize=dim; minimumSize=dim},
      constraints(0, 11, gridwidth=3, fill=GridBagPanel.Fill.Both))
  }

}

object PietInterpreter {
  def main(args: Array[String]) {
    val ui = new UI
    ui.centerOnScreen()
    ui.visible = true
  }
}