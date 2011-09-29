// foo

import xml._

import xml.Node

resolvers += {
  val tsUrl = url("http://repo.typesafe.com/typesafe/ivy-snapshots")
  val pList = List(
    "[organisation]/[module]/[revision]/jars/[artifact](-[classifier]).[ext]"
  )
  sbt.Resolver.url("typesafe-snapshots", tsUrl)(Patterns(pList, pList, false))
}

seq()

scalaVersion := "2.9.0-1"

libraryDependencies += "commons-io" % "commons-io" % "1.2" % "test"

ideaBasePackage := Some("com.wizzle")

libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _)

TaskKey[Unit]("foo") <<= (packagedArtifact in Compile in ThisProject in packageBin) map { (x) => println(x) }