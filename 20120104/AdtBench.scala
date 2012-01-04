package scalaz


object AdtBench extends App {
  (1 to 5).foreach { _ =>
    MaybeFunctions.benchmark
    MaybeFunctionsInline.benchmark
    MaybeData.benchmark  
  }
}

object Util {
  def BenchmarkCycles = 100000000

  def time[Z](label: String)(f: => Z): Z = {
    val startTime = System.currentTimeMillis

    val result = f

    val endTime = System.currentTimeMillis

    println("Time for " + label + ": " + ((endTime - startTime) / 1000.0))

    result
  }
}

import Util._

object MaybeFunctions {
  sealed trait Maybe[+T] {
    def fold[Z](just: T => Z, nothing: => Z): Z

    def filter(f: T => Boolean): Maybe[T] = fold[Maybe[T]](
      just    = t => if (f(t)) Just(t) else Zilch,
      nothing = Zilch
    )

    def map[TT](f: T => TT): Maybe[TT] = fold(
      just    = t => Just(f(t)),
      nothing = Zilch
    )

    def flatMap[TT](f: T => Maybe[TT]): Maybe[TT] = fold(
      just    = t => f(t),
      nothing = Zilch
    )
  }

  def Just[T](t: T): Maybe[T] = new Maybe[T] {
    def fold[Z](just: T => Z, nothing: => Z) = just(t)
  }

  lazy val Zilch: Maybe[Nothing] = new Maybe[Nothing] {
    def fold[Z](just: Nothing => Z, nothing: => Z) = nothing
  }

  def benchmark = {
    time("functions") {
      var i = 0
      var maybe: Maybe[String] = Just("")

      while (i < BenchmarkCycles) {
        maybe = maybe.map(x => x).flatMap(j => Just(j))

        i = i + 1
      }
    }
  }
}

object MaybeFunctionsInline {
  // Must be a class, not a trait, thanks to https://issues.scala-lang.org/browse/SI-4767
  sealed abstract class Maybe[+T] {
    @inline final def fold[Z](just: T => Z, nothing: => Z): Z = this match {
      case Just(t) => just(t)
      case Zilch => nothing
    }

    final def filter(f: T => Boolean): Maybe[T] = fold[Maybe[T]](
      just    = t => if (f(t)) Just(t) else Zilch,
      nothing = Zilch
    )

    final def map[TT](f: T => TT): Maybe[TT] = fold(
      just    = t => Just(f(t)),
      nothing = Zilch
    )

    final def flatMap[TT](f: T => Maybe[TT]): Maybe[TT] = fold(
      just    = t => f(t),
      nothing = Zilch
    )
  }
  private case class Just[T](t: T) extends Maybe[T]
  private case object Zilch extends Maybe[Nothing]

  def benchmark = {
    time("functions inline") {
      var i = 0
      var maybe: Maybe[String] = Just("")

      while (i < BenchmarkCycles) {
        maybe = maybe.map(x => x).flatMap(j => Just(j))

        i = i + 1
      }
    }
  }
}

object MaybeData {
  sealed trait Maybe[+T] {
    def filter(f: T => Boolean): Maybe[T] = this match {
      case Just(t) if (f(t)) => Just(t)

      case _ => Zilch
    }

    def map[TT](f: T => TT): Maybe[TT] = this match {
      case Zilch   => Zilch
      case Just(t) => Just(f(t))
    }

    def flatMap[TT](f: T => Maybe[TT]): Maybe[TT] = this match {
      case Zilch   => Zilch
      case Just(t) => f(t)
    }
  }

  case class Just[T](t: T) extends Maybe[T]

  case object Zilch extends Maybe[Nothing]

  def benchmark = {
    time("data") {
      var i = 0
      var maybe: Maybe[String] = Just("")

      while (i < BenchmarkCycles) {
        maybe = maybe.map(x => x).flatMap(j => Just(j))

        i = i + 1
      }
    }
  }
}