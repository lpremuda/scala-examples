package com.example.designpatterns.typeclass

import cats.Show           //the type class
import cats.syntax.show._  //the interface syntax

import com.example.designpatterns.typeclass.models._
import CrustSize._
import CrustType._

object AlvinTC103 extends App {

  implicit val pizzaShow = Show.show[Pizza] { p =>
    s"""|Pizza(${p.crustSize}, ${p.crustType}),
        |      toppings = ${p.toppings}""".stripMargin
  }
  val pizza = Pizza(MediumCrust, ThinCrust, List(Topping("Mushroom"),Topping("Onion")))

  println("Running AlvinTC103\n")
  println(pizza.show)

}
