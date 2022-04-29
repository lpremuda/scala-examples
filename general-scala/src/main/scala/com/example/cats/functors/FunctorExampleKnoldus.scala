package com.example.cats.functors

import cats.Functor

object FunctorExampleKnoldus extends App {

  case class LineItem(price: Double)
  val orders: List[LineItem] = List(LineItem(10),LineItem(31.89),LineItem(5.5))
  val maybeOrder: Option[LineItem] = Option(LineItem(8))
  def applyDiscountToLineItem(li: LineItem): LineItem = li.copy(price = li.price * 0.9)

  // This process of overloading functions is tedious
  // This is where a Functor could help
  def calcBudget(orders: List[LineItem]) = orders.map(applyDiscountToLineItem)
  def calcBudget(maybeOrder: Option[LineItem]) = maybeOrder.map(applyDiscountToLineItem)

  println("Calculating budget via traditional method overloading:")
  println(calcBudget(orders))
  println(calcBudget(maybeOrder))
  println()

  def calcBudgetWithFunctor[F[_]](order: F[LineItem])(implicit ev: Functor[F]): F[LineItem] = {
    Functor[F].map(order)(applyDiscountToLineItem)
  }

  println("Calculating budget with Functor:")
  println(calcBudgetWithFunctor(orders))
  println(calcBudgetWithFunctor(maybeOrder))

}
