package com.example.scalawithcats.ch2MonoidsSemigroups

import cats.Monoid
import cats.instances.int._
import cats.syntax.monoid._

object SuperAdderExercise extends App {

  case class Order(totalCost: Double, quantity: Double)

  object MonoidInstances {
    implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
      def combine(x: Order, y: Order): Order = {
        Order(Monoid[Double].combine(x.totalCost, y.totalCost), Monoid[Double].combine(x.quantity, y.quantity))
      }
      def empty: Order = {
        Order(Monoid[Double].empty, Monoid[Double].empty)
      }
    }
  }

  object SuperAdder {
    def add[A](items: List[A])(implicit monoid: Monoid[A]): A = {
      items.foldLeft(Monoid[A].empty)(_ |+| _)
    }
  }

  println(s"SuperAdder.add(List(4,1,5,-8)) = ${SuperAdder.add(List(4,1,5,-8))}")

  println(s"SuperAdder.add(List(Some(3),None,Some(2))) = ${SuperAdder.add(List(Some(3),None,Some(2)))}")

  import MonoidInstances._
  println(s"SuperAdder.add(List(Some(3),None,Some(2))) = ${SuperAdder.add(List(Order(5.2, 4),Order(3.4, 10)))}")

}
