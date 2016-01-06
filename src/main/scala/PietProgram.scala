class PietProgram {

  var dp:DP = RIGHT
  println(dp.x)

  dp = dp.next
  println(dp.x)
}

sealed trait DP { val x:Int; val y:Int; val next:DP }
case object RIGHT extends DP { val x = 1; val y = 0; val next = DOWN }
case object DOWN extends DP { val x = 0; val y = 1; val next = LEFT }
case object LEFT extends DP { val x = -1; val y = 0; val next = UP }
case object UP extends DP { val x = 0; val y = -1; val next = RIGHT }
