//package com.example.cats.monads
//
//import cats._
//
//object MonadExample extends App {
//
//
//  implicit def optionMonad(implicit app: Applicative[Option]): Monad[Option] =
//    new Monad[Option] {
//      // Define flatMap using Option's flatten method
//      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
//        app.map(fa)(f).flatten
//
//      // Reuse this definition from Applicative.
//      override def pure[A](a: A): Option[A] = app.pure(a)
//    }
//}
