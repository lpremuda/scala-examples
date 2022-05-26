package com.example
package fibers

import cats.effect.{IO, IOApp}

object Fibers extends IOApp.Simple {

  val meaningOfLife = IO.pure(42)
  val favLang = IO.pure("Scala")



  def sameThreadIOs() = for {
    _ <- meaningOfLife.debug
    _ <- favLang
  } yield ()

  override def run = sameThreadIOs
}
