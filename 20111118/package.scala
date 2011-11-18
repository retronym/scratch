package object foo {
	type X = Y[Int]
	object X {
		implicit def XM: M[X] = new M[X] {}
	}
}

