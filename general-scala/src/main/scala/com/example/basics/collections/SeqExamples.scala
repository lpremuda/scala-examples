package com.example.basics.collections

object SeqExamples extends App {

  val seq: Seq[Int] = Seq(1,2,3,5,6)

  val seqFoldLeft: Seq[String] = seq.foldLeft[Seq[String]](Seq.empty)((acc, el) => {
    acc :+ s"foldLeft $el"
  })

  val seqMap: Seq[String] = seq.map(el => s"map $el")

  val seqZipped: Seq[(String, String)] = seqFoldLeft.zip(seqMap)
  val mapZipped: Map[String, String] = seqZipped.toMap

  val seqCombined: Seq[(String, Int)] = seqFoldLeft.zipWithIndex

  val contains5: Boolean = seq.contains(5)
  val allAbove0: Boolean = seq.forall(_ > 0)
  val allAbove3: Boolean = seq.forall(_ > 3)

  seqFoldLeft.foreach(println)
  seqMap.foreach(println)
  seqZipped.foreach(println)
  println(mapZipped)
  seqCombined.foreach(println)
  println(s"seq contains 5: $contains5")
  println(s"elements in seq are all above 0: $allAbove0")
  println(s"elements in seq are all above 3: $allAbove3")
  println(seq.mkString(","))
  println(seq.mkString(" - "))
  println(seq.mkString)

}
