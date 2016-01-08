import scala.Array._

class PietProgram {

  var codelsArray = ofDim[Int](4, 4)
  for (i <- 0 to 3) {
    for (j <- 0 to 3) {
      codelsArray(i)(j) = j + i + 1
    }
  }

  val navigator = new PietNavigator(codelsArray)
  val stack = new PietStack

  def makeOp(from:Int, to:Int) = {
    val hueChange = ((to/6)-(from/6))%6
    val lightnessChange = (to-from)%3

    (hueChange, lightnessChange) match {
      case (0, 0) => None     //nic
      case (0, 1) => stack.push(42)    //TODO wartosc
      case (0, 2) => stack.pop
      case (1, 0) => stack.add
      case (1, 1) => stack.sub
      case (1, 2) => stack.mul
      case (2, 0) => stack.div
      case (2, 1) => stack.mod
      case (2, 2) => stack.not
      case (3, 0) => stack.greater
      case (3, 1) => navigator.pointer(stack.pop)
      case (3, 2) => navigator.switch(stack.pop)
      case (4, 0) => stack.dup
      case (4, 1) => stack.roll
      case (4, 2) => None //in int
      case (5, 0) => None //in char
      case (5, 1) => None //out int
      case (5, 2) => None //out char
    }

  }

}
