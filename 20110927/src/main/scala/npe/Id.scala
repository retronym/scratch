package npe

trait N[X]

trait IDs {
	val id = new N[ID] {}
}

object ID extends IDs 