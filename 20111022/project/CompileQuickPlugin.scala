import sbt._
import Keys._
import Compiler._

object CompileQuickPlugin extends Plugin {
  lazy val compileQuick = InputKey[Unit]("compile-quick", "forces compilation of *only* the given file(s) with a the compiler options in scalac-options(for compile-quick)")

  lazy val compileQuickSettings: Seq[Project.Setting[_]] = compileQuickSettings(Compile) ++ compileQuickSettings(Test)

  def compileQuickSettings(conf: Configuration): Seq[Project.Setting[_]] = Seq(    
    scalacOptions in compileQuick <<= scalacOptions,
    // TODO Parser to tab-complete source files
    compileQuick <<= inputTask { (argTask: TaskKey[Seq[String]]) =>
      (argTask, scalacOptions in compileQuick, Keys.compilers, dependencyClasspath in Compile, 
        classDirectory in Compile, streams) map { 
          (args: Seq[String], options: Seq[String], c: Compilers, cp, output, s) =>
            val sources = args.map(file)
            c.scalac(sources, cp.map(_.data), output, options, noopCallback, 1000, s.log)
        }
    }
  )

  object noopCallback extends xsbti.AnalysisCallback {
    def beginSource(source: File) {}

    def generatedClass(source: File, module: File, name: String) {}

    def api(sourceFile: File, source: xsbti.api.SourceAPI) {}

    def sourceDependency(dependsOn: File, source: File) {}

    def binaryDependency(binary: File, name: String, source: File) {}

    def endSource(sourcePath: File) {}
  }
}
