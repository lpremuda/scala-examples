package com.example.basics.recursion

import scala.annotation.tailrec

object AnagramRecursionHashmap extends App {

  def isAnagram(s1: String, s2: String): Boolean = {
    val seq1: Seq[(String, Int)] = s1.toLowerCase.split("").toSeq.map(s => s -> 1)
    val seq2: Seq[(String, Int)] = s2.toLowerCase.split("").toSeq.map(s => s -> 1)

    @tailrec
    def constructMap(
      seq: Seq[(String, Int)],
      map: Map[String, Int] = Map.empty[String, Int]
    ): Map[String, Int] = {
      if (seq.isEmpty) map
      else {
        val key: String = seq.head._1
        val newMap = map + map
          .get(key)
          .map(count => (key, count + 1))
          .getOrElse(seq.head)
        constructMap(seq.tail, newMap)
      }
    }

    val map1: Map[String, Int] = constructMap(seq1)
    val map2: Map[String, Int] = constructMap(seq2)

    map1 == map2
  }

  println(isAnagram("listen", "silent")) // true
  println(isAnagram("Lucas", "Cool")) // false
  println(isAnagram("", "Cool")) // false
  println(isAnagram("saDdEr", "DREADS")) // true

}
