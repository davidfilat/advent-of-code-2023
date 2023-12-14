(ns advent-of-code-2023.solutions.day4-test
  (:require [advent-of-code-2023.solutions.day4 :refer :all]
            [advent-of-code-2023.util.common :as util]
            [clojure.test :refer :all]))

(deftest parse-numbers-test
  (is (= (parse-numbers "41 48 83 86 17") [41 48 83 86 17])))

(deftest test-parse-card-line
  (testing "Parsing card line"
    (let
      [result
         (parse-card-line
           "Card   1: 82 41 56 54 18 62 29 55 34 20 | 37 14 10 80 58 11 65 96 90  8 59 32 53 21 98 83 17  9 87 25 71 77 70 73 24")]
      (is (= (:card-number result) 1))
      (is (= (:winning-numbers result) [82 41 56 54 18 62 29 55 34 20]))
      (is (= (:my-numbers result)
             [37 14 10 80 58 11 65 96 90 8 59 32 53 21 98 83 17 9 87 25 71 77 70
              73 24])))))

(def test-input
  ["Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53"
   "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
   "Card 3: 1 21 53 59 44 | 69 82 63 72 16 21 14 1"
   "Card 4: 41 92 73 84 69 | 59 84 76 51 58 5 54 83"
   "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
   "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"])

(deftest process-card-stack-test
  (let [parsed-cards (map parse-card-line test-input)
        card-stack (vec parsed-cards)
        processed-stack (process-card-stack card-stack)]
    (is (= (map :copies processed-stack) [1 2 4 8 14 1]))))

(deftest get-intersection-test
  (let [card {:winning-numbers [83 86 6 31 17 9 48 53],
              :my-numbers [41 48 83 86 17]}]
    (is (= (get-intersection card) #{83 86 48 17}))))

(deftest get-score-test (is (= (get-score [83 86 48 17]) 8)))

(deftest get-card-score-test
  (let [card {:winning-numbers [83 86 6 31 17 9 48 53],
              :my-numbers [41 48 83 86 17]}]
    (is (= (get-card-score card) 8))))

(deftest count-winning-numbers-test
  (let [card {:winning-numbers [83 86 6 31 17 9 48 53],
              :my-numbers [41 48 83 86 17]}]
    (is (= (count-winning-numbers card) 4))))

(deftest solution-part1-test
  (with-redefs [util/read-input-file (fn [_] test-input)]
    (is (= (solution-part1 test-input) 13))))

(deftest solution-part1-test
  (with-redefs [util/read-input-file (fn [_] test-input)]
    (is (= (solution-part2 test-input) 30))))
