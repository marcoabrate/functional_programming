package recfun

object Main {
  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      // binomial coefficient of (r c)
      def binomial(n:Int, k:Int): Int =
        if (k==0) return 1
        else if (n==0) return 0
        else return binomial(n-1, k-1)+binomial(n-1, k)
      
      binomial(r, c)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def recBalance(h:Char, recChars:List[Char], num:Int): Boolean =
        if (num < 0) return false
        else
        if (recChars.isEmpty)
          h match {
            case '(' => if (num+1 == 0) return true else return false
            case ')' => if (num-1 == 0) return true else return false
            case whatever => if (num == 0) return true else return false
          }
        else
          h match {
            case '(' => return recBalance(recChars.head, recChars.tail, num+1)
            case ')' => return recBalance(recChars.head, recChars.tail, num-1)
            case whatever => return recBalance(recChars.head, recChars.tail, num)
          }

      if (chars.isEmpty) return false
      return recBalance(chars.head, chars.tail, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int =
      if (money == 0) 1
      else if (money < 0 || coins.isEmpty) 0
      else countChange(money, coins.tail) +
            countChange(money-coins.head, coins)
  }
