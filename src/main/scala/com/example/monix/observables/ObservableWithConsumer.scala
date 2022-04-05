package com.example.monix.observables

import cats.data.Chain
import cats.effect.ExitCode
import monix.eval.{Task, TaskApp}
import monix.reactive.{Consumer, Observable}

object ObservableWithConsumer extends TaskApp {

  private val maxNumOfSubscribers: Int = 10
  println(s"Max number of subscribers: $maxNumOfSubscribers")

  val testData1: List[Array[Int]] = List(
    Array(1,2,3,4,5,6,7,8,9),
    Array(10,11,12,13,14,15,16,17,18,19),
    Array(20,21,22,23,24,25,26,27,28,29)
  )

  val testData2: List[Array[Int]] = List(
    Array(1,2,3,34,5,6,7,8,9),
    Array(10,11,12,13,55,15,16,17,18,19),
    Array(20,21,22,23,24,25,26,27,28,29)
  )

  val testData3: List[Array[Int]] = List(
    Array(1,2,3,34),
    Array(5,6,7,8,9),
    Array(10,11,12,13,55),
    Array(15,16,17,18,19),
    Array(20,21,22,23,24),
    Array(25,26,27,28,29)
  )

  // 1. Create an Observable of Array[Int]
  val arrayIntGenerator: Observable[Array[Int]] = Observable.fromIterable(testData3)

  // 2. Create a Consumer.Sync
  def generateConsumer: Consumer.Sync[Array[Int], Chain[Int]] = {
    // This consumer folds over each set of Array[Int]. It picks the highest value from each one and adds it to a
    // list that we will flatten later
    Consumer.foldLeft[Chain[Int], Array[Int]](Chain.empty)(
      (existingInts: Chain[Int], newInts: Array[Int]) => {
        println(s"existsInts: $existingInts")
        // Pick the value from the Array[Int].
        Option(newInts.max)
          // Discard ints with a value <= 5
          .filter(_ > 5)
          .map(el => {
            // Append the newest int result to the chain of existing ints
            println(s"Adding $el")
            existingInts :+ el
          })
          .getOrElse(existingInts)
      }
    )
  }

  val mySyncConsumer: Consumer.Sync[Array[Int], Chain[Int]] = generateConsumer

  // 3. Convert to Consumer
  def createPrimaryConsumer(
                       mySyncConsumer: Consumer.Sync[Array[Int], Chain[Int]]
                     ): Consumer[Array[Int], Option[Int]] = {
    /*
      This merges the results of the "scoring" of the Array[Int]
      Run the Sync Consumer process over the stream of input tasks in parallel

      Signature:
      def loadBalance[A, R](parallelism: Int, consumer: Consumer[A, R]): Consumer[A, List[R]]

      Creates a consumer that, when consuming the stream, will start multiple subscribers and distribute load between them.
      Once
     */
    val loadBalancedConsumer: Consumer[Array[Int], List[Chain[Int]]] = Consumer
      .loadBalance[Array[Int], Chain[Int]](parallelism = maxNumOfSubscribers, mySyncConsumer)

    // After all Array[Int] have been processed by the consumer, flatten the results
    val loadBalancedConsumerFolded: Consumer[Array[Int], Chain[Int]] = loadBalancedConsumer.map(chains => {
      println("Flattening List[Chain[Int]] to Chain[Int]")
      println(s"\tList[Chain[Int]] = $chains")
      chains.foldLeft[Chain[Int]](Chain.empty)(_ ++ _)
    })

    // Pick the highest value from each of the Array[Int]
    val finalConsumer: Consumer[Array[Int], Option[Int]] = loadBalancedConsumerFolded.map(chainInt => {
      println("Choosing highest value of chain of ints")
      println(s"\tChain[Int] = $chainInt")
      chainInt match {
        case Chain.nil => None
        case chainInts => Some(chainInts.toVector.maxBy(int => int))
      }
    })

    finalConsumer
  }

  val myConsumer: Consumer[Array[Int], Option[Int]] = createPrimaryConsumer(mySyncConsumer)

  // 4. Consume the Observable with Consumer
  val resultingTask: Task[Option[Int]] = arrayIntGenerator.consumeWith[Option[Int]](myConsumer)


  def run(args: List[String]): Task[ExitCode] = {
    val mainTask: Task[Unit] = for {
      result <- resultingTask
      _ <- Task { println(s"Final result: $result") }
    } yield ()

    mainTask.map(_ => ExitCode.Success)
  }

}
