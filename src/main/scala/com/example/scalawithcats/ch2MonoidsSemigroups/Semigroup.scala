package com.example.scalawithcats.ch2MonoidsSemigroups

trait Semigroup[A] {
  def combine(x: A, y: A): A
}
