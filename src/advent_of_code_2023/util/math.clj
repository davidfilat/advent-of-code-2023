(ns advent-of-code-2023.util.math)

(defn gcd
  "Find the greatest common divisor"
  ^Number [^Number a ^Number b]
  (if (zero? b) a (recur b (mod a b))))

(defn lcm
  "Find the lowest common multiplier"
  ^Number [^Number a ^Number b]
  (/ (* a b) (gcd a b)))
