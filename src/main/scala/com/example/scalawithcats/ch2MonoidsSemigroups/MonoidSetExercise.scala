package com.example.scalawithcats.ch2MonoidsSemigroups

object MonoidSetExercise extends App {

  object MonoidInstances {
    object MonoidSetInstances {
      // This is a valid Monoid[Set] instance
      object MonoidSetInstance1 {
        implicit def booleanMonoid1[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
          def combine(x: Set[A], y: Set[A]): Set[A] = x union y
          def empty: Set[A] = Set.empty[A]
        }
      }
    }
  }

  import MonoidInstances.MonoidSetInstances.MonoidSetInstance1._
  println(s"Does the associative law hold true? True or false: ${MonoidLaws.associativeLaw(Set(3,1), Set(-10, 22), Set.empty[Int])}")
  println(s"Does the identity law hold true? True or false: ${MonoidLaws.identityLaw(Set(5,2))}")

  println(s"Does the associative law hold true? True or false: ${MonoidLaws.associativeLaw(Set("A","B"), Set("C", "D"), Set("E", "F"))}")
  println(s"Does the identity law hold true? True or false: ${MonoidLaws.identityLaw(Set("A","B"))}")

}
