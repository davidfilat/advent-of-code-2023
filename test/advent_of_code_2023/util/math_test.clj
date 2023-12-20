(ns advent-of-code-2023.util.math-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2023.util.math :as math]))

(deftest test-gcd
  (testing "Greatest Common Divisor"
    (is (= 5 (math/gcd 10 5)))
    (is (= 1 (math/gcd 17 13)))
    (is (= 12 (math/gcd 36 24)))))

(deftest test-lcm
  (testing "Least Common Multiple"
    (is (= 20 (math/lcm 10 20)))
    (is (= 221 (math/lcm 17 13)))
    (is (= 72 (math/lcm 36 24)))))