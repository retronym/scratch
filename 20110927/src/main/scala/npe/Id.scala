package npe

trait M[X[_]]

trait IDs {
	val id = new M[ID] {}
}

object ID extends IDs 