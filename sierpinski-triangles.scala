class Triangle(val rowStart: Int,
               val rowEnd: Int,
               val colStart: Int,
               val colEnd: Int){}

class Drawer() {
  val rowCount = 32
  val colCount = 63
  var canvasMatrix = Array.ofDim[Char](32, 63)
  var state = 0
  var triangles = List[Triangle]()

  {
    for (i <- 0 to rowCount - 1){
      for (j <- 0 to colCount - 1){
        this.canvasMatrix(i)(j) = '_'
      }
    }
  }

  private def updateMiddleSegments(row: Int,
                                   colStart: Int,
                                   colEnd: Int,
                                   length: Int,
                                   value: Char): Unit = {
    val fullLength = colEnd - colStart + 1
    val subLength = (fullLength - length) / 2

    val i = row
    val rowStart = colStart + subLength
    val rowEnd = colEnd - subLength
    for(j <- rowStart to rowEnd)
      this.canvasMatrix(i)(j) = value
  }

  private def createNewSubTriangles(rowStart: Int,
                                    rowEnd: Int,
                                    colStart: Int,
                                    colEnd: Int): List[Triangle] ={
    val subTriangleHeight = (rowEnd - rowStart + 1)/2
    val subTriangleBase = (colEnd - colStart + 1 - 1)/2
    val voidRowStart = rowEnd
    val voidRowEnd = rowEnd - subTriangleHeight + 1

    var counter = 0
    for (i <- voidRowStart to voidRowEnd by - 1){
      this.updateMiddleSegments(row = i,
                                colStart = colStart + counter,
                                colEnd = colEnd - counter,
                                length = 2 * counter + 1,
                                value = '_')
      counter += 1
    }

    List[Triangle](
      new Triangle(rowStart = rowStart,
                  rowEnd = rowStart + subTriangleHeight - 1,
                  colStart = colStart + subTriangleBase - (subTriangleBase - 1)/2,
                  colEnd = colStart + subTriangleBase + (subTriangleBase - 1)/2),
      new Triangle(rowStart = rowStart + subTriangleHeight,
                  rowEnd = rowEnd,
                  colStart = colStart,
                  colEnd = colStart + subTriangleBase - 1),
      new Triangle(rowStart = rowStart + subTriangleHeight,
                  rowEnd = rowEnd,
                  colStart = colStart + subTriangleBase + 1,
                  colEnd = colEnd),
    )

  }

  def plot(): Unit ={
    for (i <- 0 to this.rowCount - 1){
      for (j <- 0 to this.colCount - 1){
        print(this.canvasMatrix(i)(j))
      }
      println()
    }
  }

  def update(): Unit ={
    if (this.state > 5)
      return

    // Initial State
    if (this.state == 0){
      for (i <- 0 to this.rowCount - 1)
        this.updateMiddleSegments(row = i,
                                  colStart = 0,
                                  colEnd = this.colCount - 1,
                                  length = 2 * i + 1,
                                  value = '1')
      this.triangles = List[Triangle]()
      this.triangles = this.triangles :+ new Triangle(0,
                                                      rowEnd = this.rowCount - 1,
                                                      colStart = 0,
                                                      colEnd = this.colCount - 1)
    }else{
      // For States >= 1
      var newTriangles = List[Triangle]()
      for(triangle <- this.triangles){
        newTriangles = newTriangles ++ this.createNewSubTriangles(rowStart = triangle.rowStart,
                                                                  rowEnd = triangle.rowEnd,
                                                                  colStart = triangle.colStart,
                                                                  colEnd = triangle.colEnd)

      }
      this.triangles = newTriangles
    }
    this.state += 1
  }
}

object Solution {
  def drawTriangles(n: Int) {
    val drawer = new Drawer()
    (0 to n).foreach(_ => drawer.update())
    drawer.plot()
  }

  def main(args: Array[String]) {
    drawTriangles(readInt())
  }
}

