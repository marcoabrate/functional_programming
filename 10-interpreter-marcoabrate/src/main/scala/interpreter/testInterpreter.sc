// Note: "Run this worksheet" does not recompile other files in this project,
// You should run "~compile" in sbt to automatically recompile the project.

import interpreter._
import Lisp._

object Code {
  val code1 =
"""
(* (+ 3 3) 7)
"""

  val code2 =
"""
(def factorial (lambda (x)
  (if  (= x 0) 1 (* x (factorial (- x 1)))))
(factorial 6))
"""

  val code3 =
"""
(def (sum x y) (+ x y) (sum 2 3))
"""

  val code4 =
"""
(case 0
  (1 (+ 0 1))
  (2 (+ 1 1))
  (3 (+ 1 2))
  (else (+ 2 2))
)
"""

val test = 
"(case (case 1 (2 50) (3 4) (else 5)) (3 51) (5 100) (else 0))"

  val reverse =
"""
(def (reverse L acc) (
  if (null? L) acc 
  (reverse (cdr L) (cons (car L) acc))
) (
  reverse (cons 1 (cons 2 (cons 3 (quote ())))) (quote ())
))
"""

val differences = 
"""
(def (differences L) (
  if (null? L) L
  (def (trecDiff Ll acc elem) (
      if (null? Ll) acc
      (trecDiff (cdr Ll) (cons (- (car Ll) elem) acc) (car Ll))
    ) (
      reverse (trecDiff (cdr L) (cons (car L) (quote ())) (car L)) (quote ())
  ))
) (
  differences (cons 1 (cons 5 (cons 9 (cons 2 (quote ())))))
))
"""

 val rebuildList =
 """
  (def (rebuildList L) (
    if (null? L) L
    (def (trecRL Ll acc elem) (
        if (null? Ll) acc
        (trecRL (cdr Ll) (cons (+ (car Ll) elem) acc) (+ elem (car Ll)))
      ) (
        reverse (trecRL (cdr L) (cons (car L) (quote ())) (car L)) (quote ())
    ))
  ) (
    rebuildList nil
  ))
  """
}
import Code._

// Uncomment to enable tracing of each evaluation step
// trace = true

// Display the parser output
println(string2lisp(code1))
// Run the interpreter
println(evaluate(code1))

println(string2lisp(code2))
println(evaluate(code2))

val op = 'def :: List('name, 'x) :: 'body :: 'expr :: Nil
println(string2lisp(code3))
println(evaluate(code3))

println(string2lisp(code4))
println(evaluate(code4))

println(evaluate(reverse))

println(evaluate(differences))

println(evaluate(rebuildList))

println(string2lisp(test))
println(evaluate(test))