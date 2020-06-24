name := """location-api"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.7" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
libraryDependencies += "com.github.tomakehurst" % "wiremock-standalone" % "2.25.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
