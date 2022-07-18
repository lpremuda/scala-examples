import scala.collection.mutable

object PascalsTriangle extends App {

  def generate(numRows: Int): List[List[Int]] = {

    val buffer: mutable.Buffer[List[Int]] = mutable.Buffer.fill(numRows)(List.empty[Int])
    buffer.update(0, List(1))

    for (i <- Range(1, numRows)) {
      val prevRow: List[Int] = buffer(i-1)
      val rowToAdd: mutable.Buffer[Int] = mutable.Buffer.fill(prevRow.length+1)(0)
      for (j <- prevRow.indices.init) {
        rowToAdd.update(j+1, prevRow(j) + prevRow(j+1))
      }
      rowToAdd.update(0, 1)
      rowToAdd.update(prevRow.length, 1)

      buffer.update(i, rowToAdd.toList)
    }

    buffer.toList
  }

  generate(5).foreach(println)


}
