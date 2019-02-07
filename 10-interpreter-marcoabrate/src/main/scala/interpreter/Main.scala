package interpreter

import Lisp._
import java.io.{BufferedReader, InputStreamReader}

class Console {
  def console(in:BufferedReader): Unit = {
      print("REPL> ")
      val input = in.readLine()
      if (input != "exit") {
        println(evaluate(input))
        println()
        console(in)
      }
  }
}

object Main {
  val in = new BufferedReader(new InputStreamReader(System.in))
  val console_obj = new Console

  object ExitLoop extends Exception {}
  def main(args: Array[String]): Unit = {
    console_obj.console(in)
  }
}

object LispCode {
    // TODO: implement the function `reverse` in Lisp.
  // From a list (a, b, c, d) it should compute (d, c, b, a)
  // Write it as a String, and test it in your REPL
  val reverse = """
  def (reverse L acc) (
    if (null? L) acc 
    (reverse (cdr L) (cons (car L) acc))
  )
  """

    // TODO: implement the function `differences` in Lisp.
  // From a list (a, b, c, d) it should compute (a, b-a, c-b, d-c)
  // You might find useful to define an inner loop def
  val differences =
  """
  def (differences L) (
    if (null? L) L
    (def (trecDiff Ll acc elem) (
        if (null? Ll) acc
        (trecDiff (cdr Ll) (cons (- (car Ll) elem) acc) (car Ll))
      ) (
        reverse (trecDiff (cdr L) (cons (car L) (quote ())) (car L)) (quote ())
    ))
  )
  """
  /*
    (5, 9, 2) -> (5, 4, -7)
    (5, 4, -7) -> (5, 9, 2)
    (a, b, c) -> (a, b+a, )
  */
  // From a list (a, b-a, c-b, d-c) it should compute (a, b, c, d)
  // From a list (a, b, c, d) it should compute (a, b+a, c+b+a, d+c+b+a)
  val rebuildList =
  """
  def (rebuildList L) (
    if (null? L) L
    (def (trecRL Ll acc elem) (
        if (null? Ll) acc
        (trecRL (cdr Ll) (cons (+ (car Ll) elem) acc) (+ elem (car Ll)))
      ) (
        reverse (trecRL (cdr L) (cons (car L) (quote ())) (car L)) (quote ())
    ))
  )
  """

  val withDifferences: String => String =
    (code: String) => "(" + reverse + " (" + differences + " (" + rebuildList + " " + code + ")))"
}
