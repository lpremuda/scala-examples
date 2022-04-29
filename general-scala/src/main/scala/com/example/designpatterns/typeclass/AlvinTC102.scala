package com.example.designpatterns.typeclass

import com.example.designpatterns.typeclass.models._

object AlvinTC102 {

  trait ToString[A] {
    def toString(a: A): String
  }

  object ToStringInstances {
    implicit val pizzaToStringInstance = new ToString[Pizza] {
      def toString(p: Pizza): String = {
        s"""|Pizza(${p.crustSize}, ${p.crustType}),
            |      toppings = ${p.toppings}""".stripMargin
      }
    }
  }

  object ToStringSyntax {
    implicit class ToStringOps[A](a: A) {
      // Note: If you called this method "toString", the built-in "toString" method would override this one.
      // Hence, we call it "asString"
      // This also proves that this method name doesn't need to match the type class's metho name
      def asString(implicit toStringInstance: ToString[A]): String = toStringInstance.toString(a)
    }
  }

  def main(args: Array[String]): Unit = {
    import CrustSize._
    import CrustType._
    import ToStringInstances.pizzaToStringInstance
    import ToStringSyntax.ToStringOps

    val pizza = Pizza(LargeCrust, PanCrust, List(Topping("Pepperoni"),Topping("Olive")))

    println("'build-in' toString method")
    println(pizza.toString)

    println()

    println("'extra' method from implicit class ToStringOps")
    println(pizza.asString)
  }

}
