package com.example.lectures.youtube.scalaworld.typeclassinduction

trait Named[A] {
  def name: String
}

object Named {
  type EOL

  // Base case
  implicit val eol: Named[EOL] = new Named[EOL] {
    def name: String = ""
  }

  implicit val int: Named[Int] = new Named[Int] {
    def name: String = "int"
  }

  implicit val char: Named[Char] = new Named[Char] {
    def name: String = "char"
  }

  implicit val string: Named[String] = new Named[String] {
    def name: String = "str"
  }

  implicit def inductionStep[Head, Tail](
    implicit
    namedHead: Named[Head],
    namedTail: Named[Tail]
  ): Named[(Head, Tail)] = new Named[(Head, Tail)] {
    def name: String = s"${namedHead.name}, ${namedTail.name}"
  }
}
