
//resolvers += {
//  val tsUrl = url("http://repo.typesafe.com/typesafe/ivy-snapshots")
//  val pList = List(
//    "[organisation]/[module]/[revision]/jars/[artifact](-[classifier]).[ext]"
//  )
//  sbt.Resolver.url("typesafe-snapshots", tsUrl)(Patterns(pList, pList, false))
//}

scalaVersion := "2.9.0-1"

libraryDependencies += "commons-io" % "commons-io" % "1.2"