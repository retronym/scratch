import com.github.retronym.sbtxjc.SbtXjcPlugin
import org.sbtidea.SbtIdeaPlugin
import sbt._
import Keys._

object build extends Build {

  val mySetting = SettingKey[String]("my-setting", "super setting!")

  lazy val sharedSettings = Defaults.defaultSettings ++ Seq[Project.Setting[_]](
    organization := "com.acme",
    mySetting := "",
    mySetting <<= (organization, scalaVersion)((o: String, sv: String) => o + sv)
  )



  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = sharedSettings ++ Seq[Project.Setting[_]](
       libraryDependencies ++= Seq(
         "junit" % "junit" % "4.4" % "test"
       )
    )
  )
}
