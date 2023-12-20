(ns advent-of-code-2023.solutions.day9-test
  (:require [advent-of-code-2023.solutions.day9 :refer :all]
            [clojure.test :refer :all]))

(deftest test-parse-input
  (testing "Parses input"
    (is (= (parse-input ["0 3 6 9 12 15" "1 3 6 10 15 21"])
           [[0 3 6 9 12 15] [1 3 6 10 15 21]]))))

(deftest test-calculate-differences
  (testing "Calculates differences"
    (is (= (calculate-differences [0 3 6 9 12 15]) [3 3 3 3 3]))
    (is (= (calculate-differences [1 3 6 10 15 21]) [2 3 4 5 6]))
    (is (= (calculate-differences [10 13 16 21 30 45]) [3 3 5 9 15]))))

(deftest test-calculate-all-differences
  (testing "Calculates all differences"
    (is (= (calculate-all-differences [0 3 6 9 12 15]) [[3 3 3 3 3] [0 0 0 0]]))
    (is (= (calculate-all-differences [1 3 6 10 15 21])
           [[2 3 4 5 6] [1 1 1 1] [0 0 0]]))))

(deftest test-solution-part1
  (testing "Solution for part 1"
    (is (= (solution-part1 (parse-input ["0 3 6 9 12 15" "1 3 6 10 15 21"
                                         "10 13 16 21 30 45"]))
           114))))

(deftest test-calculate-prev-number
  (testing "Calculates previous number"
    (is (= (calculate-prev-number [[0 3 6 9 12 15] [3 3 3 3 3] [0 0 0 0]]) -3))
    (is (= (calculate-prev-number [[10 13 16 21 30 45] [3 3 5 9 15] [0 2 4 6]
                                   [2 2 2] [0 0]])
           5))
    (is (= (calculate-prev-number [[0 0 0] [0 0 0] [0 0 0]]) 0))))

(deftest test-solution-part2
  (testing "Solution for part 2"
    (is (= (solution-part2 (parse-input ["0 3 6 9 12 15" "1 3 6 10 15 21"
                                         "10 13 16 21 30 45"]))
           2))))
