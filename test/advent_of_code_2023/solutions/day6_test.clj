(ns advent-of-code-2023.solutions.day6-test
  (:require [advent-of-code-2023.solutions.day6 :as day6]
            [clojure.test :refer :all]))

(def test-input ["Time: 7 15  30" "Distance:  9 40  200"])

(deftest test-parse-input
  (testing "parse-input function"
    (is (= (day6/parse-input test-input) '((7 15 30) (9 40 200))))))

(deftest test-calculate-distance
  (testing "calculate-distance function"
    (is (= (day6/calculate-distance 7 15) 56))))

(deftest test-calculate-distances-for-each-charging-time-limit
  (testing "calculate-distances-for-each-charging-time-limit function"
    (is (= (day6/calculate-distances-for-each-charging-time-limit 7)
           '(0 6 10 12 12 10 6 0)))))

(deftest test-get-winning-charging-times
  (testing "get-winning-charging-times function"
    (is (= (day6/get-winning-charging-times 7 9) '(10 12 12 10)))))

(deftest test-solution-part1
  (testing "solution-part1 function"
    (is (= (day6/solution-part1 test-input) 288))))

(deftest test-solution-part2
  (testing "solution-part2 function"
    (is (= (day6/solution-part2 test-input) 71503))))