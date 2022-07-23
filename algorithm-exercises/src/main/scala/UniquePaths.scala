object UniquePaths extends App {
  def uniquePaths(m: Int, n: Int): Int = {

    def numPaths(m: Int, n: Int, mem: Map[(Int, Int), Int]): Map[(Int, Int), Int] = {
      // if mem contains the key, do nothing and pass back the mem
      if (mem.contains((m, n))) mem
      // if you've reached the bottom edge or right edge, there is only one path for you to take
      else if (m == 0 || n == 0) mem ++ Map((m, n) -> 1, (n, m) -> 1)
      // numPaths moving down plus numPaths moving right
      else {
        val leftMem: Map[(Int, Int), Int] = numPaths(m, n-1, mem)
        val rightMem: Map[(Int, Int), Int] = numPaths(m-1, n, leftMem)

        val totalPaths: Int = rightMem(m, n-1) + rightMem(m-1, n)
        rightMem + ((m, n) -> totalPaths)
      }
    }

    // initialize Map with base cases
    val mem: Map[(Int, Int), Int] = Map(
      (1, 0) -> 1,
      (0, 1) -> 1
    )
    // m x n is the size of the grid, so you want to start at coordinated (m-1, n-1), i.e. 0-indexed
    val finalMem = numPaths(m-1, n-1, mem)

    finalMem.getOrElse((m-1, n-1), throw new RuntimeException("Error occurred getting final value"))
  }

  println(uniquePaths(3,7)) // 28
  println(uniquePaths(3,2)) // 3
  println(uniquePaths(13,23)) // 548354040
}
