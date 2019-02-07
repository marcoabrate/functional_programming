package recfun

import org.junit._
import org.junit.Assert.assertEquals

class RecFunSuite {
  // ------ balance tests -----------------------------------------------------

  import Main.balance

  @Test def `balance: '(if (zero? x) max (/ 1 x))' is balanced`: Unit =
    assert(balance("(if (zero? x) max (/ 1 x))".toList))

  @Test def `balance: 'I told him ...' is balanced`: Unit =
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))

  @Test def `balance: ':-)' is unbalanced`: Unit =
    assert(!balance(":-)".toList))

  @Test def `balance: counting is not enough`: Unit =
    assert(!balance("())(".toList))
  
  // ------ countChange tests -------------------------------------------------

  import Main.countChange

  @Test def `countChange: example given in instructions`: Unit =
    assertEquals(countChange(4,List(1,2)), 3)

  @Test def `countChange: sorted CHF`: Unit =
    assertEquals(countChange(300,List(5,10,20,50,100,200,500)), 1022)

  @Test def `countChange: no pennies`: Unit =
    assertEquals(countChange(301,List(5,10,20,50,100,200,500)), 0)

  @Test def `countChange: unsorted CHF`: Unit =
    assertEquals(countChange(300,List(500,5,50,100,20,200,10)), 1022)
  
  // ------ pascal tests ------------------------------------------------------

  import Main.pascal

  @Test def `pascal: col=0,row=2`: Unit =
    assertEquals(pascal(0,2), 1)

  @Test def `pascal: col=1,row=2`: Unit =
    assertEquals(pascal(1,2), 2)

  @Test def `pascal: col=1,row=3`: Unit =
    assertEquals(pascal(1,3), 3)

    @Test def `pascal: col=2,row=4`: Unit =
    assertEquals(pascal(2,4), 6)
  
  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}

