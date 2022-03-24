package com.example.scalawithcats.ch2MonoidsSemigroups

object MonoidBooleanExercise extends App {

  object MonoidInstances {
    object MonoidBooleanInstances {
      // This is a valid Monoid[Boolean] instance
      object MonoidBooleanInstance1 {
        implicit val booleanMonoid1: Monoid[Boolean] = new Monoid[Boolean] {
          def combine(x: Boolean, y: Boolean): Boolean = x && y
          def empty: Boolean = true
        }
      }

      // This is NOT a valid Monoid[Boolean] instance
      object MonoidBooleanInstance2 {
        implicit val booleanMonoid2: Monoid[Boolean] = new Monoid[Boolean] {
          def combine(x: Boolean, y: Boolean): Boolean = x || y
          def empty: Boolean = true
        }
      }

      // This is NOT a valid Monoid[Boolean] instance
      object MonoidBooleanInstance3 {
        implicit val booleanMonoid2: Monoid[Boolean] = new Monoid[Boolean] {
          def combine(x: Boolean, y: Boolean): Boolean = x && y
          def empty: Boolean = false
        }
      }

      // This is a valid Monoid[Boolean] instance
      object MonoidBooleanInstance4 {
        implicit val booleanMonoid2: Monoid[Boolean] = new Monoid[Boolean] {
          def combine(x: Boolean, y: Boolean): Boolean = x || y
          def empty: Boolean = false
        }
      }
    }
  }

  import MonoidInstances.MonoidBooleanInstances.MonoidBooleanInstance1._
//  import MonoidInstances.MonoidBooleanInstances.MonoidBooleanInstance2._
//  import MonoidInstances.MonoidBooleanInstances.MonoidBooleanInstance3._
//  import MonoidInstances.MonoidBooleanInstances.MonoidBooleanInstance4._
  println(s"Does the associative law hold true? True or false: ${MonoidLaws.associativeLaw(true, false, true)}")
  println(s"Does the identity law hold true? True or false: ${MonoidLaws.identityLaw(true)}")
  println(s"Does the identity law hold true? True or false: ${MonoidLaws.identityLaw(false)}")

}
