object Cyclic {
  type ID[X] = X;
  // error: cyclic aliasing or subtyping involving type ID
  val res1: ID[ID[Int]] = 0
}
