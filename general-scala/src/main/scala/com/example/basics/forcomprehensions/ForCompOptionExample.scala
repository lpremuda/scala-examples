package com.example.basics.forcomprehensions

object ForCompOptionExample extends App {

  val optionSum = for {
    a <- Option(1)
    b <- Option(2)
    c <- Option(3)
  } yield {
    a + b + c
  }

  println(optionSum)

  val optionSumExplicit: Option[Int] =
    Option.apply[Int](4).flatMap[Int] { a =>
      Option.apply[Int](5).flatMap[Int] { b =>
        Option.apply[Int](6).map[Int] { c =>
          a + b + c
        }
      }
    }

  val optionSumExplicit2: Option[Int] =
    Option.apply[Int](4).flatMap[Int] { a =>
      Option.apply[Int](5).flatMap[Int] { b =>
        Option.apply[Int](a+b+6)
      }
    }

  val optionSumExplicit3: Option[Int] =
    Option.apply[Int](4).flatMap[Int] { a =>
      Option.apply[Int](5).flatMap[Int] { b => Option(a+b+6) }
    }

  val optionSumExplicit4: Option[Int] =
    Option.apply[Int](4).flatMap[Int] { a => Option(15) }

  val optionSumExplicit5: Option[Int] =
    Option(15)

  println(optionSumExplicit)
  println(optionSumExplicit2)
  println(optionSumExplicit3)
  println(optionSumExplicit4)
  println(optionSumExplicit5)

}
