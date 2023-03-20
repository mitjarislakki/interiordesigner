package ID.projects

class Pos(val x: Int, val y: Int, val z: Int):
  def offsetBy(offset: (Int, Int, Int)) = Pos(x+offset._1, y+offset._2, z+offset._3)


