import sbt._
import Keys._

object Build extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name := "fractal",
    organization := "fractal",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.10.0",

    // dependencies
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "1.9.2" % "test",
      "org.mockito" % "mockito-all" % "1.8.4" % "test"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true

  )
}
