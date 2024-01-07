(ns advent-of-code-2023.solutions.day12-test
  (:require [advent-of-code-2023.solutions.day12 :refer :all]
            [clojure.test :refer :all]))

(def test-inputs
  ["???.### 1,1,3" ".??..??...?##. 1,1,3" "?#?#?#?#?#?#?#? 1,3,1,6"
   "????.#...#... 4,1,1" "????.######..#####. 1,6,5" "?###???????? 3,2,1"])

(deftest test-parse-input
  (testing "parse-input function"
    (is (= (parse-input [(first test-inputs)])
           [[[nil nil nil false true true true] [1 1 3]]]))))

(deftest test-count-arrangements
  (testing "count-arrangements function"
    (let [expected-outputs [1 4 1 1 4 10]
          actual-outputs (map (partial apply count-arrangements)
                           (parse-input test-inputs))]
      (is (= expected-outputs actual-outputs)))))

(deftest test-solution-part1
  (testing "solution-part1 function" (is (= (solution-part1 test-inputs) 21))))

(deftest test-repeat-with-separator
  (testing "repeat-with-separator function"
    (is (= (repeat-with-separator [1 2 3] 0 2) [1 2 3 0 1 2 3]))
    (is (= (repeat-with-separator ["a" "b" "c"] "-" 3)
           ["a" "b" "c" "-" "a" "b" "c" "-" "a" "b" "c"]))
    (is (= (repeat-with-separator [] 0 2) []))
    (is (= (repeat-with-separator ["a" "b" "c"] "-" 1) ["a" "b" "c"]))))

(deftest test-unfold-row
  (testing "unfold-row function"
    (is (= (unfold-row [[false true] [1]])
           [[false true nil false true nil false true nil false true nil false
             true] [1 1 1 1 1]]))))

(deftest test-solution-part2
  (testing "solution-part2 function"
    (is (= (solution-part2 test-inputs) 525152))))

(run-tests 'advent-of-code-2023.solutions.day12-test)
