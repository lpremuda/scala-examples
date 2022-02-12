package com.example.designpatterns.typeclass

import com.example.designpatterns.typeclass.AlvinTC101.BehavesLikeHumanSyntax.BehavesLikeHunanInterface

// 1. Create trait Animal with subclasses
// 2. Create type class
// 3. Create instance of the type class that makes dog talk like human
// 4. Create functions that you want consumers of your API to see

object AlvinTC101 {

  sealed trait Animal
  final case class Dog(name: String) extends Animal
  final case class Cat(name: String) extends Animal
  final case class Bird(name: String) extends Animal

  trait BehavesLikeHuman[A] {
    def speak(a: A): String
  }

  object BehavesLikeHumanInstances {
    implicit val dogBehavingLikeHuman = new BehavesLikeHuman[Dog] {
      def speak(dog: Dog): String = s"Woof, my name is ${dog.name}!"
    }
  }

  object BehavesLikeHuman {
    def speak[A](a: A)(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): String = {
      behavesLikeHumanInstance.speak(a)
    }
  }

  object BehavesLikeHumanSyntax {
    implicit class BehavesLikeHunanInterface[A](a: A) {
      def speak(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): String = {
        behavesLikeHumanInstance.speak(a)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    import BehavesLikeHumanInstances.dogBehavingLikeHuman
    val dog = Dog("Spike")
    println(BehavesLikeHuman.speak(dog))
    println(dog)

    println("Option 3b")
    println(dog.speak)
  }

}
