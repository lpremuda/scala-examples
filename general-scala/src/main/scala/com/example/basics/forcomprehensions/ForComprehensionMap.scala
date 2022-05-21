package com.example.basics.forcomprehensions

object ForComprehensionMap extends App {

  val seq: Seq[Int] = Seq(3,4,5,6)

  val newSeq: Seq[Int] =
    for {
      x <- seq
    } yield x * 2

  val newSeqMap: Seq[Int] = seq.map(x => x * 2)
  val newSeqMap2: Seq[Int] = seq.map(_ * 2)

  // newSeq, newSeqMap, and newSeqMap2 should all be the same

  println(seq)
  println(newSeq)
  println(newSeqMap)
  println(newSeqMap2)

}
