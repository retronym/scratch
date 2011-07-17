import sbt._
import Keys._
import sbt.Load.LoadedPlugins

object build extends Build {
  val runMainInDir = InputKey[Unit]("run-main-in-dir", "Runs the main class selected by the first argument in a specific working directory, passing the remaining arguments to the main method.")
  val workingDirectory = SettingKey[File]("working-directory", "The working directory a forked process")

  // Mostly duplicated from Defaults.runnerInit, which assumes the workind direcory is the baseDirectory
  def runnerInit0 = (scalaInstance, workingDirectory, javaOptions, outputStrategy, javaHome, trapExit) {
    (si, working, options, strategy, javaHomeDir, trap) =>
      new ForkRun(ForkOptions(scalaJars = si.jars, javaHome = javaHomeDir, outputStrategy = strategy,
        runJVMOptions = options, workingDirectory = Some(working))): ScalaRun
  }

  val root = Project(id = "root", base = file("."),
    settings = Defaults.defaultSettings ++ Seq[Project.Setting[_]](
      // Variation of "run-main" that starts in a specified working directory
      (workingDirectory in runMainInDir) := file("/"),
      inTask(runMainInDir)((runner <<= runnerInit0) :: Nil).head,
      runMainInDir <<= Defaults.runMainTask(fullClasspath in Runtime, runner in runMainInDir)
    )
  )
}

/*
> inspect working-directory
[info] No entry for key.
[info] Description:
[info] 	The working directory a forked process
[info] Delegates:
[info] 	{file:/Users/jason/code/scratch/20110704/}root/*:working-directory
[info] 	{file:/Users/jason/code/scratch/20110704/}/*:working-directory
[info] 	*/*:working-directory
[info] Related:
[info] 	{file:/Users/jason/code/scratch/20110704/}root/*:working-directory(for run-main-in-dir)
> show {file:/Users/jason/code/scratch/20110704/}root/*:working-directory(for run-main-in-dir)
[info] /
> eval "cat /Users/jason/code/scratch/20110704/src/main/scala/Test.scala" !
import java.io.File

object Test extends Application {
  println("the main event in: " + new File(".").getAbsolutePath)
}[info] ans: Int = 0
> run-main-in-dir Test
[info] the main event in: /.
[success] Total time: 1 s, completed Jul 17, 2011 10:02:48 AM
*/