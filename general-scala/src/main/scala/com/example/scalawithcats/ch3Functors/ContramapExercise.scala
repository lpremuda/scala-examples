package com.example.scalawithcats.ch3Functors

object ContramapExercise extends App {

  trait Printable[A] { self =>
    def format(a: A): String

    def contramap[B](f: B => A): Printable[B] =
      new Printable[B] {
        def format(b: B): String = {
          self.format(f(b))
        }
      }
  }

  implicit val intPrintable: Printable[Int] = new Printable[Int] {
    def format(int: Int): String = {
      int.toString
    }
  }

  implicit val stringPrintable: Printable[String] = new Printable[String] {
    def format(str: String): String = {
      s"'${str}'"
    }
  }

  implicit val booleanPrintable: Printable[Boolean] = new Printable[Boolean] {
    def format(bool: Boolean): String = {
      if (bool) "yes" else "no"
    }
  }

  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
    p.contramap[Box[A]](_.value)

  def format[A](value: A)(implicit p: Printable[A]): String = {
    p.format(value)
  }

  println(format(234))
  println(intPrintable.contramap[Int](_ * 2).format(234))
  println(format("hello"))
  println(format(true))
  // The below line resembles how "boxPrintable[A]" is defined
//  println(intPrintable.contramap[Box[Int]](b => b.value).format(Box(3)))
  println(format(Box(4)))
  println(format(Box("lucas")))
  // The below line will cause an error because a Printable[Double] instance does not exist, therefore a Printable[Box[Double]]
  // instance could not be constructed
//  println(format(Box(5.toDouble)))

}
