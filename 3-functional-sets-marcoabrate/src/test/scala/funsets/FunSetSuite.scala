package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * @Ignore annotation.
   */
  @Test def `singleton set one contains one`: Unit = {
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton 1")
      assert(contains(s3, 3), "Singleton 2")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      assert(contains(s_union, 1), "Union 1")
      assert(contains(s_union, 2), "Union 2")
      assert(!contains(s_union, 3), "Union 3")

      val s_union2 = union(s_union, s3)
      assert(contains(s_union2, 1), "Union 4")
      assert(contains(s_union2, 2), "Union 4b")
      assert(contains(s_union2, 3), "Union 4c")
      assert(!contains(s_union2, 16), "Union 5")

      printSet(s_union2)
    }
  }

  @Test def `intersection contains elements of A and B`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s2, s3)
      val s_int = intersect(s_union, s_union2)
      assert(contains(s_int, 2), "Intersect 1")
      assert(!contains(s_int, 1), "Intersect 2")
      assert(!contains(s_int, 3), "Intersect 3")

      printSet(s_int)
    }
  }

  @Test def `difference contains elements of A minus B`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s_union, s3)
      val s_diff = diff(s_union2, s1)
      assert(contains(s_diff, 2), "Diff 1")
      assert(contains(s_diff, 3), "Diff 2")
      assert(!contains(s_diff, 1), "Diff 3")

      printSet(s_diff)
    }
  }

  @Test def `filter contains elements of A that respect the predicate p`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s_union, s3)
      val s_filter = filter(s_union2, x => x%2==1)
      assert(contains(s_filter, 1), "Filter 1")
      assert(contains(s_filter, 3), "Filter 2")
      assert(!contains(s_filter, 2), "Filter 3")

      printSet(s_filter)
    }
  }

  @Test def `forall checks all elements of A`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s_union, s3)
      assert(forall(s_union2, a => a > 0), "Forall 1")
      assert(!forall(s_union2, a => a < 0), "Forall 2")
      assert(!forall(s_union2, a => a%2 == 1), "Forall 3")

      val s_1 = singletonSet(-1)
      val s_union3 = union(s_union2, s_1)
      assert(!forall(s_union3, a => a > 0), "Forall 4")
      assert(forall(s_union3, a => a > -2), "Forall 5")
    }
  }

  @Test def `exists check the existence of a predicate in A`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s_union, s3)
      assert(exists(s_union2, a => a > 0), "Exists 1")
      assert(!exists(s_union2, a => a < 0), "Exists 2")
      assert(exists(s_union2, a => a%2 == 0), "Exists 3")

      val s_1 = singletonSet(-1)
      val s_union3 = union(s_union2, s_1)
      assert(exists(s_union3, a => a < 0), "Exists 4")
      assert(!exists(s_union3, a => a < -2), "Exists 5")
    }
  }

  @Test def `map changes the set A to another set mapA`: Unit = {
    new TestSets {
      val s_union = union(s1, s2)
      val s_union2 = union(s_union, s3)
      val mapS = map(s_union2, x => x*x)
      assert(contains(mapS, 1), "Map 1")
      assert(contains(mapS, 4), "Map 2")
      assert(contains(mapS, 9), "Map 3")
      assert(!contains(mapS, 2), "Map 4")

      printSet(mapS)
    }
  }


  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}

