(ns advent-of-code-2023.solutions.day14-test
  (:require [advent-of-code-2023.solutions.day14 :refer :all]
            [clojure.test :refer :all]))

(def test-input
  ["O....#...." "O.OO#....#" ".....##..." "OO.#O....O" ".O.....O#." "O.#..O.#.#"
   "..O..#O..O" ".......O.." "#....###.." "#OO..#...."])
(deftest test-parse-input
  (let [expected-output [true nil nil nil nil false nil nil nil nil]]
    (is (= (nth (parse-input test-input) 0) expected-output))))

(deftest test-rotate-grid
  (let [input (parse-input test-input)
        directions [:north :east :south :west]
        actual-output (reduce (fn [acc direction] (rotate-grid direction acc))
                        input
                        directions)]
    (is (= actual-output input))))

(deftest test-move-stones-to-the-end
  (let [input [true nil true nil nil nil]
        expected-output [nil nil nil nil true true]]
    (is (= (move-stones-to-the-end input) expected-output))))

(deftest test-move-stones-in-column
  (let [input (nth (parse-input test-input) 2)
        expected-output [nil nil nil nil nil false false nil nil nil]
        actual-output (move-stones-in-column input)]
    (is (= actual-output expected-output))))

(deftest test-calculate-load-per-column
  (let [input [nil nil nil false true nil nil nil false nil true nil true true
               true nil nil nil nil nil true]]
    (is (= (calculate-load-per-column input) 79))))

(deftest test-solution-part1 (is (= (solution-part1 test-input) 136)))

(deftest test-spin-cycle
  (let [input (parse-input test-input)
        expected-output [[nil nil nil nil nil false nil nil nil nil]
                         [nil nil nil nil false nil nil nil true false]
                         [nil nil nil true true false false nil nil nil]
                         [nil true true false nil nil nil nil nil nil]
                         [nil nil nil nil nil true true true false nil]
                         [nil true false nil nil nil true false nil false]
                         [nil nil nil nil true false nil nil nil nil]
                         [nil nil nil nil nil nil true true true true]
                         [false nil nil nil true false false false nil nil]
                         [false nil nil true true false nil nil nil nil]]]
    (is (= (spin-cycle input) expected-output))))

(deftest test-calculate-total-load
  (let [input (parse-input test-input)]
    (is (= (calculate-total-load input) 104))))

(deftest test-solution-part2 (let [] (is (= (solution-part2 test-input) 64))))

(run-tests 'advent-of-code-2023.solutions.day14-test)
