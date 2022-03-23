package com.example.scalawithcats.introduction

import cats.Eq
import cats.syntax.eq._

object EqExercise extends App {

  implicit val catEq: Eq[Cat] = Eq.instance[Cat] { (cat1, cat2) =>
    cat1.name === cat2.name   &&
    cat1.age === cat2.age     &&
    cat1.color === cat2.color
  }

  val c1 = Cat("Fred", 30, "white")
  val c2 = Cat("Fred", 30, "white")
  val c3 = Cat("Whiskers", 30, "white")
  val c4 = Cat("Fred", 25, "white")

  val isC1C2Same: Boolean = c1 === c2
  val isC1C3Same: Boolean = c1 === c3
  val isC1C4Same: Boolean = c1 === c4

  println(isC1C2Same)
  println(isC1C3Same)
  println(isC1C4Same)

  println("\nUsing traditional == syntax. Note: This actually still works because we are using a case class.")
  println(c1 == c2)

  println("\nUsing traditional == syntax. With a normal class this time.")
  class Dog(val name: String, val age: Int)
  val dog1 = new Dog("Jasper", 6)
  val dog2 = new Dog("Jasper", 6)
  println(dog1 == dog2)

  println("\nExercise Results:")
  val cat1 = Cat("Garfield",   38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")

  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  println(cat1 === cat2)
  println(optionCat1 === optionCat2)

}
