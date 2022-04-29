package com.example.basics.recursion

import scala.annotation.tailrec

object AnagramRecursion extends App {

  def isAnagram(s1: String, s2: String): Boolean = {
    val seq1: Seq[String] = s1.toLowerCase.split("").toSeq
    val seq2: Seq[String] = s2.toLowerCase.split("").toSeq

    @tailrec
    def anagramTR(seq1: Seq[String], seq2: Seq[String], stillIsAnagram: Boolean = false): Boolean = {
      if (seq1.isEmpty || seq2.isEmpty) stillIsAnagram
      else {
        val el: String = seq1.head
        if (seq2.contains(el)) anagramTR(seq1.tail, seq2.diff(Seq(el)), true)
        else false
      }
    }

    if (seq1.length != seq2.length) false
    else anagramTR(seq1, seq2)
  }

  println(isAnagram("listen", "silent"))  // true
  println(isAnagram("Lucas", "Cool"))     // false
  println(isAnagram("", "Cool"))          // false
  println(isAnagram("saDdEr", "DREADS"))  // true

}
