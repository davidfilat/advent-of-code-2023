(ns advent-of-code-2023.util.grid-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2023.util.grid :as grid]))

(deftest test-get-next-coordinates
  (testing "get-next-coordinates function"
    (is (= (grid/get-next-coordinates :north [1 1]) [0 1]))
    (is (= (grid/get-next-coordinates :south [1 1]) [2 1]))
    (is (= (grid/get-next-coordinates :west [1 1]) [1 0]))
    (is (= (grid/get-next-coordinates :east [1 1]) [1 2]))))
