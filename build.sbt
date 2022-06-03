import sbt.Keys.libraryDependencies

name := "scala-examples"

ThisBuild / version := "0.1"

// Added per instruction of Scala with Cats
ThisBuild / scalacOptions ++= Seq("-Xfatal-warnings")
/*
  Note:
    Originally was using 2.13.8
    Moved to 2.13.1 (what Scala with Cats uses) because of a deprecation error I was getting when working with Eval type class
 */
ThisBuild / scalaVersion := "2.13.1"

val versionCirce = "0.13.0"

lazy val `scala-examples` = project
  .in(new File("."))

lazy val `general-scala` = project
  .settings(
    // Note: Scala with Cats uses 2.1.0
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.3.0",
    // Note: Originally used cats-effect 3.3.5. However, when I added monix 3.2.2, there was a binary incompatibility,
    // so I reverted to cats-effect 2.1.3
    //libraryDependencies ++= Seq("org.typelevel" %% "cats-effect" % "3.3.5")
    libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.3",
    libraryDependencies += "io.monix" %% "monix" % "3.2.2",
    libraryDependencies += "io.circe" %% "circe-core" % versionCirce,
    libraryDependencies += "io.circe" %% "circe-generic" % versionCirce,
    libraryDependencies += "io.circe" %% "circe-generic-extras" % versionCirce,
    libraryDependencies += "io.circe" %% "circe-parser" % versionCirce
  )

lazy val `cats-effect` = project
  .in(new File("cats-effect"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.2.0"
  )