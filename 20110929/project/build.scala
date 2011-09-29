import sbt._
import Keys._

object build extends Build {
  // Setting shared by all projects
  lazy val standardSettings = Defaults.defaultSettings ++ Seq(
    // The `console` analog to `Defaults.mainRunTask` and `Defaults.mainRunMainTask`
    // It's omission from the standard settings seems to be an oversight. 
    console <<= consoleTask(fullClasspath in Runtime, console)
  )
  
  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = standardSettings,
    aggregate = Seq(a, b)
  )

  lazy val a = Project(
    id = "a",
    base = file("a"),
    settings = standardSettings
  )

  lazy val b = Project(
    id = "b",
    base = file("b"),
    settings = standardSettings,
    // Depend on the `runtime` scope transitively
    dependencies = Seq(a % "compile->compile;runtime->runtime")
  )

  import Build.data
  import Project.Initialize

  // Copy/Paste from Defaults.consoleTask, allowing `classpath : ScopedTask` rather than `: Task`.
  def consoleTask(classpath: ScopedTask[Classpath], task: TaskKey[_]): Initialize[Task[Unit]] = {

    (compilers in task, classpath, scalacOptions in task, initialCommands in task, streams) map {
      (cs, cp, options, initCommands, s) =>
        (new Console(cs.scalac))(data(cp), options, initCommands, s.log).foreach(msg => sys.error(msg))
        println()
    } 
  }
}