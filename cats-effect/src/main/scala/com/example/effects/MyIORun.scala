package com.example.effects

import scala.io.StdIn

object MyIORun extends App {

  // An IO which returns the current time of the system
  val clock: MyIO[Long] = MyIO(() => System.currentTimeMillis())

  // An IO which measures the duration of a computation
  def measure[A](computation: MyIO[A]): MyIO[Long] = {
    for {
      start <- clock
      _ <- computation
      end <- clock
    } yield end - start
  }

  // An IO which prints something to the console
  def printToConsole(message: String): MyIO[Unit] = {
    MyIO(() => println(message))
  }

  val printLucas: MyIO[Unit] = printToConsole("Lucas")

  // An IO which reads a line from the standard input
  val readLine: MyIO[String] = MyIO(() => StdIn.readLine())


    /*
      clock.flatMap(start => {
        computation.flatMap(_ => {
          clock.map(end => {
            end - start
          })
        })
      })

      f = start => {
        computation.flatMap(_ => {
          clock.map(end => {
            end - start
          })
        })
      }

      MyIO(() => f(System.currentTimeMillis).unsafeRun())
      MyIO(() => start => {
        MyIO(() => _ => {
          MyIO(() => end => {
            end - start
          }
        }.unsafeRun())
      }.unsafeRun())

      MyIO(() => start => {
        computation.flatMap(_ => {
          clock.map(end => {
            end - start
          })
        })
      }.unsafeRun())
     */

}
