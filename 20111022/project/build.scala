import sbt._
import Keys._

object build extends Build {
  lazy val root = Project(
    "scratch", 
    file("."),
    settings = Defaults.defaultSettings ++ CompileQuickPlugin.compileQuickSettings
  )
}
