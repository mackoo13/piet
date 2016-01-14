import scala.swing._
import javax.swing.ImageIcon
import Array._

class UI extends MainFrame {

  var codelsArray = ofDim[Int](4, 4)
  codelsArray(0) = Array(0, 1, 9, 0)
  codelsArray(1) = Array(0, 2, 0, 10)
  codelsArray(2) = Array(1, 0, 17, 3)
  codelsArray(3) = Array(1, 1, 2, 5)

  val inputField = new TextField { columns = 2 }
  val program = new PietProgram(this)
  val codels = new Codels(4, 4, codelsArray)
//  for(j <- 0 to 2) program.step()
  var i = 0

  val l1 = new Label("bla %d".format(i))
  val labelDP = new Label("DP: %s".format(program.nav.dp.name))
  val labelCC = new Label("CC: %s".format(program.nav.cc.name))
  val labelCurrentCoords = new Label("COORDS: %s".format(program.nav.currentCodel))
  val labelNextCoords = new Label("NEXT: %s".format(program.nav.next()))
  val labelStack = new Label("<html>STACK:</html>")
  val labelOut = new Label("<html>OUT:</html>")

  preferredSize = new Dimension(620, 500)
  title = "Piet"

  def step() = {
    program.step()
    labelDP.text = "DP: %s".format(program.nav.dp.name)
    labelCC.text = "CC: %s".format(program.nav.cc.name)
    labelCurrentCoords.text = "COORDS: %s".format(program.nav.currentCodel)
    labelNextCoords.text = "NEXT: %s".format(program.nav.next())
    labelStack.text = "<html>STACK:<br>%s</html>".format(program.stack)
    labelOut.text = "<html>OUT:<br>%s</html>".format(program.out)
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

    add(Button("step") {step()},
      constraints(0, 0))
    add(labelDP, constraints(0, 1))
    add(labelCC, constraints(0, 2))
    add(labelCurrentCoords, constraints(0, 3))
    add(labelNextCoords, constraints(0, 4))
    add(labelStack, constraints(0, 5, fill=GridBagPanel.Fill.Vertical))
    add(codels, constraints(1, 0, gridheight=6, weightx = 1.0, weighty=1.0, fill=GridBagPanel.Fill.Both))
    add(inputField,
      constraints(2, 0, weightx=1.0, fill=GridBagPanel.Fill.Horizontal))
    add(labelOut, constraints(2, 1, gridheight=5, fill=GridBagPanel.Fill.Vertical))
  }

//  contents = new GridPanel(3, 2) {
//    contents += new Label {
//      icon = new ImageIcon("src/main/student.png")
//    }
//    contents += Button("A Button") {i+=1; l1.text = "ble %d".format(i)}
//    contents += codels
//    contents += l1
//    contents += new CheckBox("Check me!")
//    contents += Button("Close") { sys.exit(0) }
//  }
}

object GuiProgramOne {
  def main(args: Array[String]) {
    val ui = new UI
    ui.visible = true
  }
}