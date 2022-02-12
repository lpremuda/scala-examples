package com.example.designpatterns.typeclass

// 1. Create trait Animal with subclasses
// 2. Create type class
// 3. Create instance of the type class that makes dog talk like human
// 4. Create functions that you want consumers of your API to see

object AlvinTC101 {

  sealed trait Animal
  final case class Dog(name: String) extends Animal
  final case class Cat(name: String) extends Animal
  final case class Bird(name: String) extends Animal

  // Type class
  trait BehavesLikeHuman[A] {
    def speak(a: A): String
  }

  // Type class instances wrapped in an object
  object BehavesLikeHumanInstances {
    implicit val dogBehavingLikeHuman = new BehavesLikeHuman[Dog] {
      def speak(dog: Dog): String = s"Woof, my name is ${dog.name}!"
    }

    implicit val catBehavingLikeHuman = new BehavesLikeHuman[Cat] {
      def speak(cat: Cat): String = s"Meow, I can actually talk."
    }
  }

  // "Interface Object" approach, or "explicit" approach
  object BehavesLikeHuman {
    def speak[A](a: A)(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): String = {
      behavesLikeHumanInstance.speak(a)
    }
  }

  // "Interface Syntax" approach
  object BehavesLikeHumanSyntax {
    implicit class BehavesLikeHumanOps[A](a: A) {
      def speak(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): String = {
        behavesLikeHumanInstance.speak(a)
      }
    }
  }

  def main(args: Array[String]): Unit = {

    // Import type class instance and implicit class
    import BehavesLikeHumanInstances.{dogBehavingLikeHuman,catBehavingLikeHuman}
    import BehavesLikeHumanSyntax.BehavesLikeHumanOps

    // Instantiate a Dog object
    val dog = Dog("Spike")
    val cat = Cat("Whiskers")

    println("Option 3a")
    println(BehavesLikeHuman.speak(dog))
    // Proves that you can explicitly pass type class instance
    println(BehavesLikeHuman.speak(dog)(dogBehavingLikeHuman))

    println()

    println("Option 3b")
    println(dog.speak)
    // Proves that you can explicitly pass type class instance
    println(dog.speak(dogBehavingLikeHuman))

    println("\nAfter adding Cat object\n")

    println("Option 3a")
    println(BehavesLikeHuman.speak(cat))

    println()

    println("Option 3b")
    println(cat.speak)

  }

}
