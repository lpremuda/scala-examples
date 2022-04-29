package com.example.scalawithcats.ch4Monads

object MonadDefinition {

  /*

    A monad is a mechanism for sequencing computations

    - one of the most common abstractions in Scala
    - informally, its anything with a constructor and a flatMap method

    Monad Laws

    Left identity:
      pure(a).flatMap(func) == func(a)

    Right identity:
      m.flatMap(func) == m

    Associativity:
      m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))

    Extends the following 2 type classes:
      1. FlatMap for flatMap method
      2. Applicative for pure method

   */

}
