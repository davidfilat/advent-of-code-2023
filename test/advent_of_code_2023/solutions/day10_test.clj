(ns advent-of-code-2023.solutions.day10-test
  (:require [advent-of-code-2023.solutions.day10 :as day10]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(def test-grid
  [["7" "-" "F" "7" "-"] ["." "F" "J" "|" "7"] ["S" "J" "L" "L" "7"]
   ["|" "F" "-" "-" "J"] ["L" "J" "." "L" "J"]])

(deftest test-find-start-coordinates
  (testing "find-start-coordinates function"
    (is (= [2 0] (day10/find-start-coordinates test-grid)))))

(deftest test-navigate-pipes
  (testing "navigate-pipes function"
    (is (= [[2 0] [2 1] [1 1] [1 2] [0 2] [0 3] [1 3] [2 3] [2 4] [3 4] [3 3]
            [3 2] [3 1] [4 1] [4 0] [3 0]]
           (day10/navigate-pipes test-grid [2 0] :east)))))

(deftest test-navigate-all-direction
  (testing "navigate-all-direction function"
    (is (= [[2 0] [2 1] [1 1] [1 2] [0 2] [0 3] [1 3] [2 3] [2 4] [3 4] [3 3]
            [3 2] [3 1] [4 1] [4 0] [3 0]]
           (day10/navigate-all-direction [2 0] test-grid)))))

(deftest test-get-next-direction
  (testing "get-next-direction function"
    (is (= :east (day10/get-next-direction "L" :south)))
    (is (= :north (day10/get-next-direction "J" :east)))
    (is (= :west (day10/get-next-direction "7" :north)))
    (is (= :south (day10/get-next-direction "F" :west)))
    (is (= :south (day10/get-next-direction "|" :south)))))



(def test-grid2
  (let
    [raw-grid
       "FF7FSF7F7F7F7F7F---7\nL|LJ||||||||||||F--J\nFL-7LJLJ||||||LJL-77\nF--JF--7||LJLJ7F7FJ-\nL---JF-JLJ.||-FJLJJ7\n|F|F-JF---7F7-L7L|7|\n|FFJF7L7F-JF7|JL---7\n7-L-JL7||F7|L7F-7F7|\nL.L7LFJ|||||FJL7||LJ\nL7JLJL-JLJLJL--JLJ.L"]
    (day10/parse-input (str/split raw-grid #"\n"))))



(run-tests 'advent-of-code-2023.solutions.day10-test)
