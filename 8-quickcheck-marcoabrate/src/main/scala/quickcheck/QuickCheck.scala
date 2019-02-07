package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(x, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int) => 
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { (a: Int, b: Int) =>
    val h = insert(a, insert(b, empty))
    findMin(h) == scala.math.min(a, b)
  }

  property("add-delete") = forAll { (a: Int) =>
    val h = deleteMin(insert(a, empty))
    isEmpty(h)
  }

  property("add-delete2") = forAll{ (a: Int, b: Int, c: Int) =>
    val h = insert(c, insert(b, insert(a, empty)))
    findMin(deleteMin(deleteMin(h))) == scala.math.max(c, scala.math.max(a, b))
  }

  property("sorted-sequence") = forAll { (h: H) =>
    def isSorted(min: Int, h: H): Boolean = 
      if (isEmpty(h)) true
      else if (min > findMin(h)) false
      else isSorted(findMin(h), deleteMin(h))

    isSorted(findMin(h), deleteMin(h))
  }

  property("min-of-melding") = forAll { (h1: H, h2: H) =>
    val m1 = findMin(h1)
    val m2 = findMin(h2)
    val mm = findMin(meld(h1, h2))
    mm == scala.math.min(m1, m2)
  }

  property("melding-with-empty") = forAll { (a: Int) =>
    val h = insert(a, empty)
    isEmpty(deleteMin(meld(h, empty)))
  }

}
