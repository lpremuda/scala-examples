package com.example.scalawithcats.ch1Introduction

object PrintableExercise extends App {

  trait Printable[A] {
    def format(value: A): String
  }

  object PrintableInstances {
    implicit val stringPrintable: Printable[String] = new Printable[String] {
      def format(value: String): String = {
        value
      }
    }

    implicit val intPrintable: Printable[Int] = new Printable[Int] {
      def format(value: Int): String = {
        value.toString
      }
    }

    implicit val catPrintable: Printable[Cat] = new Printable[Cat] {
      def format(cat: Cat): String = {
        val name = Printable.format(cat.name)
        val age = Printable.format(cat.age)
        val color = Printable.format(cat.color)
        s"$name is a $age year-old $color cat."
      }
    }
  }

  object PrintableSyntax {
    implicit class PrintableOps[A](value: A) {
      def format(implicit p: Printable[A]): String = {
        p.format(value)
      }

      def print(implicit p: Printable[A]): Unit = {
        println(format(p))
      }
    }
  }

  object Printable {
    def format[A](value: A)(implicit p: Printable[A]): String = {
      p.format(value)
    }

    def print[A](value: A)(implicit p: Printable[A]): Unit = {
      println(format(value))
    }
  }

  import PrintableInstances._
  val aString = "This is a string."
  val formattedString = Printable.format(aString)
  println(formattedString)
  Printable.print(aString)

  val anInt = 4
  val formattedInt = Printable.format(anInt)
  println(formattedInt)
  Printable.print(anInt)

  val jessie: Cat = Cat("Jessie", 20, "black")
  Printable.print(jessie)

  println("Using PrintableSyntax")
  import PrintableSyntax._
  jessie.print

}
