(ns advent-of-code-2023.solutions.day11-test
  (:require [advent-of-code-2023.solutions.day11 :as day11]
            [clojure.test :refer :all]))

(def test-grid
  (day11/parse-input ["...#......" ".......#.." "#........." ".........."
                      "......#..." ".#........" ".........#" ".........."
                      ".......#.." "#...#....."]))

(deftest test-find-empty-rows
  (testing "find-empty-rows function"
    (is (= (day11/find-empty-rows test-grid) [3 7]))))

(deftest test-find-empty-columns
  (testing "find-empty-columns function"
    (is (= (day11/find-empty-columns test-grid) [2 5 8]))))

(deftest test-get-y-distance
  (testing "get-y-distance function"
    (let [empty-space-expansion-coef 2
          point1 [5 1]
          point2 [9 4]]
      (is (= (day11/get-y-distance test-grid
                                   empty-space-expansion-coef
                                   point1
                                   point2)
             5)))))

(deftest test-get-x-distance
  (testing "get-x-distance function"
    (let [empty-space-expansion-coef 2
          point1 [5 1]
          point2 [9 4]]
      (is (= (day11/get-x-distance test-grid
                                   empty-space-expansion-coef
                                   point1
                                   point2)
             4)))))

(deftest test-find-shortest-path-length-between-galaxies
  (testing "find-shortest-path-length-between-galaxies function"
    (let [empty-space-expansion-coef 2
          galaxy1 [5 1]
          galaxy2 [9 4]]
      (is (= (day11/find-shortest-path-length-between-galaxies
               test-grid
               empty-space-expansion-coef
               galaxy1
               galaxy2)
             9)))))
(run-tests 'advent-of-code-2023.solutions.day11-test)
