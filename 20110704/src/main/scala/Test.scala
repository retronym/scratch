import java.io.File

object Test extends Application {
  println("the main event in: " + new File(".").getAbsolutePath)
}