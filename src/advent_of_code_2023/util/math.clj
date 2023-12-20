(ns advent-of-code-2023.util.math)

;greatest common divisor
(defn gcd [a b] (if (zero? b) a (recur b (mod a b))))

;least common multiple
(defn lcm [a b] (/ (* a b) (gcd a b)))