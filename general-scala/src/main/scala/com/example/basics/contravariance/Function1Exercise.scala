package com.example.basics.contravariance

object Function1Exercise extends App {

  val animal: Animal = new Animal
  val bird: Bird = new Bird
  val duck: Duck = new Duck
  val mallard: Mallard = new Mallard

  val mallardToInt: Mallard => Int = mallard => mallard.numOfEyes
  val duckToInt: Function1[Duck, Int] = duck => duck.numOfEyes
  val birdToInt: Bird => Int = bird => bird.numOfEyes
  val animalToInt: Function1[Animal, Int] = animal => animal.numOfEyes

  def applyFunction(func: Duck => Int, duck: Duck): Int = {
    func(duck)
  }

  println("Using Duck as argument:")
//  println(applyFunction(mallardToInt, duck))        <--- causes error because of type mismatch
  println(applyFunction(duckToInt, duck))
  println(applyFunction(birdToInt, duck))
  println(applyFunction(animalToInt, duck))

  println("Using Mallard as argument:")
//  println(applyFunction(mallardToInt, mallard))     <--- causes error because of type mismatch
  println(applyFunction(duckToInt, mallard))
  println(applyFunction(birdToInt, mallard))
  println(applyFunction(animalToInt, mallard))

}
