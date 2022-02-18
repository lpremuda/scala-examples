package com.example.cats.monoids

object MonoidExample extends App {

  val listInt = List(1,2,3,4,5)
  val listString = List("hello", " world", "!")
  val listSet = List(Set(2,5),Set(10),Set(3,5,20))

  def combineInts(list: List[Int]): Int = list.foldRight(0)(_ + _)
  def combineStrings(list: List[String]): String = list.foldRight("")(_ ++ _)
  def combineSets(list: List[Set[Int]]): Set[Int] = list.foldRight(Set.empty[Int])(_ union _)

  println(combineInts(listInt))
  println(combineStrings(listString))
  println(combineSets(listSet))

  trait Monoid[A] {
    def empty: A
    def combine(a1: A,a2: A): A
  }

  object Monoid {
    def apply[A : Monoid]: Monoid[A] = implicitly[Monoid[A]]
  }

  implicit val intMonoid = new Monoid[Int] {
    def empty: Int = 0
    def combine(i1: Int,i2: Int): Int = i1 + i2
  }

//  def combineUniversal[A](list: List[A])(implicit m: Monoid[A]): A = list.foldRight(m.start)(m.combine)
  def combineUniversal[A: Monoid](list: List[A]): A = list.foldRight(Monoid[A].empty)(Monoid[A].combine)

  println("\nUsing intMonoid")
  println(combineUniversal(listInt))

}
