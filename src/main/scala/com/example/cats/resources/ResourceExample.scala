package com.example.cats.resources

//import cats.effect.kernel.Resource
import cats.effect.Resource
import cats.effect.{ExitCode, IO, IOApp}

import scala.io.{BufferedSource, Source}

object ResourceExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    // make(IO[BufferedSource])(BufferedSource => IO[Unit])
    // def make[F[_], A](acquire: F[A])(release: A => F[Unit])
    //    F[_] = IO
    //    A    = BufferedSource
    val fileResource: Resource[IO, BufferedSource] = Resource.make(
      IO(Source.fromResource("test.txt"))
    )(
      bufferedSource => IO(bufferedSource.close())
    )

    // 'use' method returns IO[Unit]
    // 'as'  method returns IO[ExitCode]
    //
    //  def use[B](f: A => F[B])(implicit F: MonadCancel[F, Throwable]): F[B]
    // A      = BufferedSource
    // F[B]   = F[Unit]
    fileResource.use(bufferedSource => IO {
      val lines: List[String] = bufferedSource.getLines().toList
      lines.foreach(println)
    }).as(ExitCode.Success)
  }


}
