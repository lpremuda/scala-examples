package com.example.scalawithcats.ch3Functors

import cats.Functor
import cats.implicits.toFunctorOps

object FunctorExericse extends App {

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = {
      Branch(left, right)
    }

    def leaf[A](value: A): Tree[A] = {
      Leaf(value)
    }
  }

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
      tree match {
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
        case Leaf(value) => Leaf(f(value))
      }
    }
  }

  // The below line fails the invariance problem discuessed in Section 1.6.1: the compiler can find an instance for
  // Tree, but not for Branch or Leaf.
//  treeFunctor.map(Branch(Leaf(10), Leaf(20)))(_ * 2)

  // Added "branch" and "leaf" constructors to correct this
  val branch1: Tree[Int] = Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2)

  println(branch1)

}
