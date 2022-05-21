package com.example.io.part1introduction.exercises

import cats.effect.IO
import cats.effect.unsafe.implicits.global

object Exercise6_SumIO extends App {
  /*
  For reference:

    def sum(n: Int): Int = {
      if (n <= 0) 0
      else n + sum(n-1)
    }

    println(sum(4)) // 10
    println(sum(5)) // 15
    println(sum(6)) // 21

   */

  def sum(n: Int): Int = {
    if (n <= 0) 0
    else n + sum(n - 1)
  }

  def sumIO(n: Int): IO[Int] = {
    if (n <= 0) IO(0)
    else
      for {
        currentValue <- IO(n)
        previousSum <- sumIO(n - 1)
      } yield previousSum + currentValue
  }

  def sumIOFlatMap(n: Int): IO[Int] = {
    if (n <= 0) IO(0)
    else IO(n).flatMap(currentValue => sumIOFlatMap(n - 1).map(previousSum => previousSum + currentValue))
  }

  println("sumIO(20000).unsafeRunSync()")
  println(sumIO(20000).unsafeRunSync())
  println()

  println("sumIOFlatMap(20000).unsafeRunSync()")
  println(sumIOFlatMap(20000).unsafeRunSync())
  println()

//  println(sum(20000)) // throws java.lang.StackOverflowError

  /*

    Example: sumIOFlatMap(3).unsafeRunSync()

      IO(3).flatMap(
        3 => sumIOFlatMap(2).map(
          previousSum => previousSum + 3
        )
      )

      IO(3).flatMap(
        3 => IO(2).flatMap(
          2 => sumIOFlatMap(1).map(
            previousSum => previousSum + 2)
          ).map(
            previousSum => previousSum + 3
          )
        )
      )

      IO(3).flatMap(
        3 => IO(2).flatMap(
          2 => IO(1).flatMap(
            1 => sumIOFlatMap(0).map(
              previousSum => previousSum + 1
            )
          ).map(
            previousSum => previousSum + 2)
          ).map(
            previousSum => previousSum + 3
          )
        )
      )

      IO(3).flatMap(
        3 => IO(2).flatMap(
          2 => IO(1).flatMap(
            1 => IO(0).map(
              previousSum => previousSum + 1
            )
          ).map(
            previousSum => previousSum + 2)
          ).map(
            previousSum => previousSum + 3
          )
        )
      )



   */

  val manualIO: IO[Int] =
    IO(3)
      .flatMap { three =>
        IO(2)
          .flatMap { two =>
            IO(1)
              .flatMap { one =>
                IO(0).map { previousSum =>
                  previousSum + one
                }
              } // .flatMap { one =>
              .map { previousSum =>
                previousSum + two
              }
          } // .flatMap { two =>
          .map { previousSum =>
            previousSum + three
          }
      } // .flatMap { three =>

  val manualIO2: IO[Int] =
    IO(3)
      .flatMap { three =>
        IO(2)
          .flatMap { two =>
            IO(1)
              .flatMap { one =>
                IO(0).map(previousSum => previousSum + one)
              }
              .map(previousSum => previousSum + two)
          }
          .map(previousSum => previousSum + three)
      } // .flatMap { three =>

  val manualIO3: IO[Int] =
    IO(3)
      .flatMap { three =>
        IO(2)
          .flatMap { two =>
            IO(1)
              .flatMap { one =>
                IO(0).map(previousSum => previousSum + one)
              }
              .map(previousSum => previousSum + two)
          }
          .map(previousSum => previousSum + three)
      } // .flatMap { three =>

  val manualIO4: IO[Int] =
    IO(2)
      .flatMap { two =>
        IO(1)
          .flatMap { one =>
            IO(0).map(previousSum => previousSum + one)
          }
          .map(previousSum => previousSum + two)
      }
      .map(previousSum => previousSum + 3)

  val manualIO5: IO[Int] =
    IO(2)
      .flatMap { two =>
        IO(1)
          .flatMap { one =>
            IO(0).map(previousSum => previousSum + one)
          }
          .map(previousSum => previousSum + 2)
      }
      .map(previousSum => previousSum + 3)

  println("manualIO")
  println(manualIO.unsafeRunSync())
  println()

  val sumIO_3: IO[Int] = {
    for {
      currentValue <- IO(3)
      previousSum <- sumIO(2)
    } yield previousSum + currentValue
  }

  val sumIO_3_FlatMap: IO[Int] = {
    IO(3).flatMap(currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal))
// sumIO(3) = IO(3).flatMap(currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal))
    // Applies this function:
    //    currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal)
    // Accumulated Result:
    //    sumIO(2).map(prevSum => prevSum + 3)
    // Now it needs to evaluate sumIO(2) next, and then apply "prevSum => prevSum + 3" map function to it
    //    IO(2).flatMap(currVal => sumIO(1).map(prevSum => prevSum + currVal))

// sumIO(2) = IO(2).flatMap(currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal))
    // Applies same function as above:
    //    currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal)
    // Accumulated Result:
// sumIO(2) = sumIO(1).map(prevSum => prevSum + 2)
    //    sumIO(1).map(prevSum => prevSum + 2).map(prevSum => prevSum + 3)

// sumIO(1) = IO(1).flatMap(currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal))
    // Applies same function as above:
    //    currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal)
    // Accumulated Result:
// sumIO(1) = sumIO(0).map(prevSum => prevSum + 1)
    //    sumIO(0).map(prevSum => prevSum + 1).map(prevSum => prevSum + 2).map(prevSum => prevSum + 3)

// sumIO(0) = IO(0).flatMap(currVal => sumIO(currVal - 1).map(prevSum => prevSum + currVal))
    // Accumulated (and Final) Result:
// sumIO(0) = IO(0)
    //    IO(0).map(prevSum => prevSum + 1).map(prevSum => prevSum + 2).map(prevSum => prevSum + 3)

    // Final Result:
    // IO(0).map(prevSum => prevSum + 1).map(prevSum => prevSum + 2).map(prevSum => prevSum + 3)

    // Evaluating the Final Result:
    // IO(1).map(prevSum => prevSum + 2).map(prevSum => prevSum + 3)
    // IO(3).map(prevSum => prevSum + 3)
    // IO(6)
    // IO(6).unsafeRunSync() = 6

  }

  println("sumIO_3")
  println(sumIO_3.unsafeRunSync())
  println()

  println("sumIO_3_FlatMap")
  println(sumIO_3_FlatMap.unsafeRunSync())
  println()

}
