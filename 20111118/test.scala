package foo

trait M[A]

trait Y[A]

object Y {
	implicit def XM: M[X] = new M[X] {}
}

object test {
	implicitly[M[X]]
}