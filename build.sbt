name := "scala-examples"

version := "0.1"

/*
  Note:
    Originally was using 2.13.8
    Moved to 2.13.1 (what Scala with Cats uses) because of a deprecation error I was getting when working with Eval type class
 */
scalaVersion := "2.13.1"

// Note: Scala with Cats uses 2.1.0
libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "2.3.0")
// Note: Originally used cats-effect 3.3.5. However, when I added monix 3.2.2, there was a binary incompatibility,
// so I reverted to cats-effect 2.1.3
//libraryDependencies ++= Seq("org.typelevel" %% "cats-effect" % "3.3.5")
libraryDependencies ++= Seq("org.typelevel" %% "cats-effect" % "2.1.3")
libraryDependencies ++= Seq("io.monix" %% "monix" % "3.2.2")

// Added per instruction of Scala with Cats
scalacOptions ++= Seq(
  "-Xfatal-warnings"
)