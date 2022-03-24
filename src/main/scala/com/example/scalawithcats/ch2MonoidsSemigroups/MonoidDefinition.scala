package com.example.scalawithcats.ch2MonoidsSemigroups

object MonoidDefinition extends App {

  /*
    Definition:
      A monoid for a type A is:
        - an operation combine with type (A, A) => A
        - an element empty of type A
   */

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def combine(x: Int, y: Int): Int = x + y
    def empty: Int = 0
  }

  println(s"Does the associative law hold true? True or false: ${MonoidLaws.associativeLaw(3,6,10)}")
  println(s"Does the identity law hold true? True or false: ${MonoidLaws.identityLaw(5)}")
  println(s"Note: If you were to change the 'combine' def to subtraction, these two laws would not hold.")

}
