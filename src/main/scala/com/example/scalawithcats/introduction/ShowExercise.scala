package com.example.scalawithcats.introduction

import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._

import java.util.Date

object ShowExercise extends App {

  val intShow: Show[Int] = Show.apply[Int]
  val stringShow: Show[String] = Show.apply[String]

  val shownInt: String = 123.show
  val shownString: String = "234".show

  // Two ways to construct a Show[Date]
//implicit val dateShow: Show[Date] = Show.show[Date](date => s"${date.getTime}ms since the epoch")
  implicit val dateShow: Show[Date] = Show.fromToString[Date]

  println(new Date().show)
  println(dateShow.show(new Date()))

  implicit val catShow: Show[Cat] = Show.show[Cat] { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }

  println(Cat("Kiki", 10, "tabby").show)

  /*
  Explanation for "234".show

  1. "234".show method is called from Show.Ops[A]:
  trait Ops[A] {
    def typeClassInstance: Show[A]
    def self: A
    def show: String = typeClassInstance.show(self)
  }

  Creates new Ops[A]:
  trait ToShowOps {
    implicit def toShow[A](target: A)(implicit tc: Show[A]): Ops[A] =
      new Ops[A] {
        val self = target
        val typeClassInstance = tc
      }
  }

 */

}
