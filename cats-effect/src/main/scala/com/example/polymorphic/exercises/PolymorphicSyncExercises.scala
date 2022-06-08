package com.example.polymorphic.exercises

import cats.effect.{IO, IOApp, MonadCancel, Sync}

import java.io.{BufferedReader, InputStreamReader}

object PolymorphicSyncExercises extends IOApp.Simple {

  // suspend basically wraps the concept of a computation into a purely functional datatype without performing the computation

  trait MySync[F[_]] extends MonadCancel[F, Throwable] {
    def delay[A](thunk: => A): F[A]
    def blocking[A](thunk: => A): F[A]

    def defer[A](thunk: => F[A]): F[A] = {
      flatMap(delay(thunk))(identity)
    }
  }

  /**
   * Exercise - write a polymorphic console
   */

  trait Console[F[_]] {
    def println[A](a: A): F[Unit]
    def readLine(): F[String]
  }

  import cats.syntax.functor._
  object Console {
    def apply[F[_]](implicit sync: Sync[F]): F[Console[F]] = sync.pure((System.in, System.out)).map {
      case (in, out) => new Console[F] {
        def println[A](a: A): F[Unit] = {
          sync.blocking(out.println(a))
        }

        def readLine(): F[String] = {
          val bufferedReader = new BufferedReader(new InputStreamReader(in))
          sync.blocking(bufferedReader.readLine())
        }
      }
    }
  }

  def consolerReader(): IO[Unit] = for {
    console <- Console[IO]
    _ <- console.println("Hi, what's your name?")
    name <- console.readLine()
    _ <- console.println(s"Hi $name, nice to meet you!")
  } yield ()

  override def run: IO[Unit] = consolerReader()
}
