package com.example.designpatterns.typeclass

object Main extends App {

  case class StudentId(id: Int)
  case class StaffId(id: Int)
  case class Score(s: Double)

  // What is a type class?
  /*
    What is a type class? A group of types that satisfy a contract typically defined by a trait
   */

  // Parametrically polymorphic because we defined it with an unbounded type A
  trait Printer[A] {
    def getString(a: A): String
  }

  // The reason we use type classes is to make programs ad-hoc polymorphic
  // So, without touching the "show" method, we can achieve increase the range of types it can handle
  def show[A](a: A)(implicit printer: Printer[A]): String = printer.getString(a)

  object Printer {
    implicit val studentPrinter = new Printer[StudentId] {
      def getString(a: StudentId): String = s"StudentId: ${a.id}"
    }

    implicit val staffPrinter = new Printer[StaffId] {
      def getString(a: StaffId): String = s"StaffId: ${a.id}"
    }

    implicit val scorePrinter = new Printer[Score] {
      def getString(a: Score): String = s"Score: ${a.s}%"
    }
  }

  val studentId = StudentId(25)
  val staffId = StaffId(12)
  val score = Score(94.2)

  println(show(studentId))
  println(show(staffId))
  println(show(score))

}
