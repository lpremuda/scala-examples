package com.example.lectures.youtube.scalaworld.typeclassinduction

import com.example.lectures.youtube.scalaworld.typeclassinduction.Named.EOL

object MasteringTypeclassInduction extends App {

  println(implicitly[Named[(String, (Int, (Char, EOL)))]].name)

  def printValue[A](implicit Lucas: Named[A]): String = {
    Lucas.name
  }

  println(printValue[(String, (Int, (Char, EOL)))])

}
