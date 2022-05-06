package com.example.basics.flatmap

object UnderstandingFlatMap extends App {

  def printSeq[A](name: String, seq: Seq[A]): Unit = {
    println(name)
    seq.foreach(println)
    println()
  }

  val seq1: Seq[Int] = Seq(1,2,3)
  val seq2: Seq[Int] = Seq(4,5,6,7)

  val product: Seq[(Int, Int)] = for {
    x <- seq1
    y <- seq2
  } yield (x, y)

  val productFlatMap = seq1.flatMap(x => seq2.map(y => (x, y)))
  val productFlatMapAlt = seq1.flatMap(x => seq2.flatMap(y => Seq((x, y))))

  printSeq("product", product)

  printSeq("productFlatMap", productFlatMap)

  printSeq("productFlatMapAlt", productFlatMapAlt)

  println("product == productFlatMap")
  println(product == productFlatMap)
  println()

  println("productFlatMap == productFlatMapAlt")
  println(productFlatMap == productFlatMapAlt)
  println()

  /*

    flatMap implementation (for reference):
    def flatMap(f: A => List[B]): List[B] = {
      f(head) ++ tail.flatMap(f)
    }

    seq1.flatMap(x => seq2.map(y => (x, y)))

    where f = x => seq2.map(y => (x, y))

    Iteration #1:
    head = 1, tail = List(2, 3)
      f(1) ++ List(2, 3).flatMap(f)
      Seq((1,4), (1,5), (1,6), (1,7)) ++ List(2, 3).flatMap(f)

    head = 2, tail = List(3)
      f(1) ++ f(2) ++ List(3).flatMap(f)
      Seq((1,4), (1,5), (1,6), (1,7)) ++ Seq((2,4), (2,5), (2,6), (2,7)) ++ List(3).flatMap(f)

    head = 3, tail = Empty
      f(1) ++ f(2) ++ f(3) ++ Empty.flatMap(f)
      Seq((1,4), (1,5), (1,6), (1,7)) ++ Seq((2,4), (2,5), (2,6), (2,7)) ++ Seq((3,4), (3,5), (3,6), (3,7)) ++ Empty.flatMap(f)

  */

  // x => seq2.map(y => (x, y))
  def f(x: Int): Seq[(Int, Int)] = seq2.map(y => (x, y))

  // x = 1
  // 1 => seq2.map(y => (1, y))
  val f1: Seq[(Int, Int)] = f(1)

  // x = 2
  // 2 => seq2.map(y => (2, y))
  val f2: Seq[(Int, Int)] = f(2)

  // x = 3
  // 3 => seq2.map(y => (3, y))
  val f3: Seq[(Int, Int)] = f(3)

  val f1f2f3: Seq[(Int, Int)] = f1 ++ f2 ++ f3

  printSeq("f1", f1)
  printSeq("f2", f2)
  printSeq("f3", f3)
  printSeq("f1f2f3", f1f2f3)

}
