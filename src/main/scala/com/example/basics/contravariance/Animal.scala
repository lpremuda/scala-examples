package com.example.basics.contravariance

trait EyedAnimal {
  def numOfEyes: Int
}

sealed abstract class Creature {
  def numOfEyes: Int
  def makeNoise: String = "I'm a creature!"
}

class Animal extends Creature with EyedAnimal {
  def numOfEyes: Int = 2
}

class Bird extends Animal {
  def canFly: Boolean = true
}

class Duck extends Bird {
  override def canFly: Boolean = false
}

class Mallard extends Duck
