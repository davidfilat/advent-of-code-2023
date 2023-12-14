(ns advent-of-code-2023.solutions.day2-test
  (:require [advent-of-code-2023.solutions.day2 :refer :all]
            [advent-of-code-2023.util.common :as util]
            [clojure.test :refer :all]))

(deftest test-parse-round
  (testing "Parsing a round of game"
    (is (= (parse-round "10 green, 5 blue") {"green" 10, "blue" 5}))
    (is (= (parse-round "1 red, 9 green, 10 blue")
           {"red" 1, "green" 9, "blue" 10}))))

(deftest test-parse-game-data
  (testing "Parsing game data"
    (is (= (parse-game-data "Game 1: 10 green, 5 blue; 1 red, 9 green, 10 blue")
           {:game-number 1,
            :rounds [{"green" 10, "blue" 5}
                     {"red" 1, "green" 9, "blue" 10}]}))))

(deftest test-get-max-number-of-marbles-for-each-color
  (testing "Getting maximum number of marbles for each color"
    (let [game {:game-number 1,
                :rounds [{"green" 10, "blue" 5}
                         {"red" 1, "green" 9, "blue" 10}]}]
      (is (= (get-max-number-of-marbles-for-each-color game)
             {"red" 1, "green" 10, "blue" 10})))))

(deftest test-all-keys-greater?
  (testing
    "Testing if all keys in one map are greater than the corresponding keys in another map"
    (is (all-keys-greater? {"red" 12, "green" 13, "blue" 14}
                           {"red" 1, "green" 10, "blue" 10}))
    (is (not (all-keys-greater? {"red" 12, "green" 8, "blue" 14}
                                {"red" 1, "green" 10, "blue" 10})))))

(deftest test-solution-part-1
  (with-redefs [util/read-input-file
                  (fn [_]
                    ["Game 1: 10 green, 5 blue; 1 red, 9 green, 10 blue"
                     "Game 2: 15 red, 9 green, 10 blue; 10 green, 5 blue"
                     "Game 3: 9 red, 9 green, 10 blue; 10 green, 5 blue"
                     "Game 4: 9 red, 9 green, 16 blue; 10 green, 3 blue"])]
    (let [result (solution-part1 "fake-filename")
          expected-result 4]
      (is (= result expected-result)))))

(deftest test-solution-part-2
  (with-redefs [util/read-input-file
                  (fn [_]
                    ["Game 1: 10 green, 5 blue; 1 red, 9 green, 10 blue"
                     "Game 2: 15 red, 9 green, 10 blue; 10 green, 5 blue"
                     "Game 3: 9 red, 9 green, 10 blue; 10 green, 5 blue"
                     "Game 4: 9 red, 9 green, 16 blue; 10 green, 3 blue"])]
    (let [result (solution-part2 "fake-filename")
          expected-result 3940]
      (is (= result expected-result)))))