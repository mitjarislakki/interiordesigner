package ID.projects

class Pos(val x: Double, val y: Double, val z: Double = 0):
  def this() = this(0, 0, 0)
  def offsetBy(offset: (Double, Double, Double)) = Pos(x+offset._1, y+offset._2, z+offset._3)
  def xyTuple = (x, y)


