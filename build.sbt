name := "teadstest"

version := "0.1"

scalaVersion := "2.11.12"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.typelevel" %% "cats-core" % "1.0.1")

