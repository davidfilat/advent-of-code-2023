(ns advent-of-code-2023.util.grid-test
  (:require [advent-of-code-2023.util.grid :as grid]
            [clojure.test :refer :all]))

(deftest test-get-next-coordinates
  (testing "get-next-coordinates function"
    (is (= (grid/get-next-coordinates :north [1 1]) [0 1]))
    (is (= (grid/get-next-coordinates :south [1 1]) [2 1]))
    (is (= (grid/get-next-coordinates :west [1 1]) [1 0]))
    (is (= (grid/get-next-coordinates :east [1 1]) [1 2]))))

(deftest test-rotate-grid
  (testing "rotate-grid function"
    (is (= (grid/pivot-grid [["a" "b"] ["c" "d"]]) [["a" "c"] ["b" "d"]]))
    (is (= (grid/pivot-grid [["1" "2" "3"] ["4" "5" "6"] ["7" "8" "9"]])
           [["1" "4" "7"] ["2" "5" "8"] ["3" "6" "9"]]))))

(deftest test-get-in-grid
  (testing "get-in-grid function"
    (let [test-grid [["a" "b" "c"] ["d" "e" "f"] ["g" "h" "i"]]]
      (is (= (grid/get-in-grid test-grid [0 0]) "a"))
      (is (= (grid/get-in-grid test-grid [1 1]) "e"))
      (is (= (grid/get-in-grid test-grid [2 2]) "i"))
      (is (= (grid/get-in-grid test-grid [3 3]) nil))
      (is (= (grid/get-in-grid test-grid [-1 -1]) nil)))))

(run-tests 'advent-of-code-2023.util.grid-test)
