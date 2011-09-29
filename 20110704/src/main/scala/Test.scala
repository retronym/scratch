import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing.Validation
import java.util.ArrayList

//def foo[A](a: A = "") = a

//foo[Int](0)

//implicit val a = ""
//
//class A[X](implicit a: X) {
////  def this() = this(0)
//}
//new A[String]
////new A()

//def foo(a: Int)(b: Int) = 0
//foo(0)("sdfs")
//
//class C(a: Int)
//new C(0)




class A(a: Int)
class B[X](a: X)
class C(a: Int) {
  def this() = this(0)
}
class D(a: Int) {
  def this(a: Boolean) = this(0)
}
class E[X](a: Option[X]) {
  def this(o: Seq[X]) = this(None)
}
class F(implicit a: Int)

new J("")

new A(0)
new A(a = 0) // okay

new A // Unspecified parameters
new A() // Unspecified parameters
new A("") // Type Mismatch
new A(0, 0) // Excess
class AA extends A("") // Type Mismatch

new B[Int] // Unspecified Value Parameter
new B // Unspecified Value Parameter
new B[Int]() // Unspecified Value Parameter
new B() // TODO Unspecified Value Parameter

{ val x = new B[Int](0); x: B[Int] }

new B[String](0) // Type Mismatch

{ val x = new B(0); x: B[Int] }

class BB1 extends B(""); new BB1 : B[String] // okay

new C(0)
new C()
new C
{ class CC extends C(0) }
{ class CC extends C }
{ class CC extends C() } 

new D(0)
new D(true)
new D() // Cannot resolve constructor
new D("") // Cannot resolve constructor

{val x = new E(Seq(0)); x: E[Int]}
{val x = new E(Some(false)); x: E[Boolean]}
new E(0) // Cannot resolve constructor

{
  implicit val i = 0
  new F
}
{
  implicit val i = 0
  new F()
}
{
  new F // TODO Cannot find implicit
}
{
  new F()  // TODO Cannot find implicit
}
new F()(0)

new J[String]("")
new J[String]("", "") // Excess arguments
new J[String](new {}) // Type mismatch
new J() // TODO Missing arguments
{val x = new J(""); x: J[String]}


// TODO subsequent argument lists