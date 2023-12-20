(ns advent-of-code-2023.solutions.day7-test
  (:require [advent-of-code-2023.solutions.day7 :refer :all]
            [clojure.test :refer :all]))

(def sample-input ["32T3K 765" "T55J5 684" "KK677 28" "KTJJT 220" "QQQJA 483"])

(deftest test-get-card-strength
  (testing "get-card-strength function"
    (is (= 0 (get-card-strength false "2")))
    (is (= 12 (get-card-strength false "A")))))

(deftest test-compare-card-strength
  (testing "compare-card-strength function"
    (is (= 0 (compare-card-strength false "2" "2")))
    (is (= 1 (compare-card-strength false "A" "2")))
    (is (= -1 (compare-card-strength false "2" "A")))))

(deftest test-compare-hand-strength
  (testing "compare-hand-strength function"
    (is (= 1
           (compare-hand-strength false
                                  ["A" "K" "Q" "J" "T"]
                                  ["2" "3" "4" "5" "6"])))
    (is (= -1
           (compare-hand-strength false
                                  ["2" "3" "4" "5" "6"]
                                  ["A" "K" "Q" "J" "T"])))
    (is (= 0
           (compare-hand-strength false
                                  ["2" "3" "4" "5" "6"]
                                  ["2" "3" "4" "5" "6"])))))

(deftest test-parse-input
  (testing "parse-input function"
    (is (= (parse-input sample-input)
           [[["3" "2" "T" "3" "K"] 765] [["T" "5" "5" "J" "5"] 684]
            [["K" "K" "6" "7" "7"] 28] [["K" "T" "J" "J" "T"] 220]
            [["Q" "Q" "Q" "J" "A"] 483]]))))

(deftest test-sort-deck
  (testing "sort-deck function"
    (let [parsed-input (parse-input sample-input)]
      (is (= (sort-deck false parsed-input)
             [[["3" "2" "T" "3" "K"] 765] [["K" "T" "J" "J" "T"] 220]
              [["K" "K" "6" "7" "7"] 28] [["T" "5" "5" "J" "5"] 684]
              [["Q" "Q" "Q" "J" "A"] 483]])))))

(deftest test-is-hand-five-of-a-kind
  (testing "is-hand-five-of-a-kind? function"
    (is (= true (is-hand-five-of-a-kind? false ["2" "2" "2" "2" "2"])))
    (is (= false (is-hand-five-of-a-kind? false ["2" "2" "2" "2" "3"])))
    (is (= true (is-hand-five-of-a-kind? true ["2" "2" "2" "J" "J"])))
    (is (= true (is-hand-five-of-a-kind? true ["2" "J" "J" "J" "J"])))
    (is (= false (is-hand-five-of-a-kind? true ["2" "2" "2" "3" "J"])))))

(deftest test-is-hand-four-of-a-kind
  (testing "is-hand-four-of-a-kind? function"
    (is (= true (is-hand-four-of-a-kind? false ["2" "2" "2" "2" "3"])))
    (is (= false (is-hand-four-of-a-kind? false ["2" "2" "2" "3" "4"])))
    (is (= true (is-hand-four-of-a-kind? true ["2" "2" "2" "J" "1"])))
    (is (= true (is-hand-four-of-a-kind? true ["2" "3" "J" "J" "J"])))))

(deftest test-is-hand-full-house
  (testing "is-hand-full-house? function"
    (is (= true (is-hand-full-house? false ["2" "2" "2" "3" "3"])))
    (is (= false (is-hand-full-house? false ["2" "2" "3" "3" "4"])))
    (is (= true (is-hand-full-house? true ["2" "2" "3" "2" "J"])))
    (is (= true (is-hand-full-house? true ["2" "2" "2" "J" "J"])))
    (is (= false (is-hand-full-house? true ["2" "2" "3" "4" "J"])))
    (is (= true (is-hand-full-house? true ["2" "3" "2" "J" "J"])))))

(deftest test-is-hand-three-of-a-kind
  (testing "is-hand-three-of-a-kind? function"
    (is (= true (is-hand-three-of-a-kind? false ["2" "2" "2" "3" "4"])))
    (is (= false (is-hand-three-of-a-kind? false ["2" "2" "3" "4" "5"])))
    (is (= true (is-hand-three-of-a-kind? true ["2" "2" "2" "J" "J"])))
    (is (= true (is-hand-three-of-a-kind? true ["2" "3" "J" "J" "J"])))
    (is (= true (is-hand-three-of-a-kind? true ["2" "2" "3" "4" "J"])))
    (is (= false (is-hand-three-of-a-kind? true ["2" "5" "3" "4" "J"])))))

(deftest test-is-hand-two-pairs
  (testing "is-hand-two-pairs? function"
    ;(is (= true (is-hand-two-pairs? false ["2" "2" "3" "3" "4"])))
    ;(is (= false (is-hand-two-pairs? false ["2" "2" "3" "4" "5"])))
    ;(is (= true (is-hand-two-pairs? true ["2" "2" "3" "J" "J"])))
    ;(is (= true (is-hand-two-pairs? true ["2" "J" "J" "J" "J"])))
    (is (= true (is-hand-two-pairs? true ["2" "2" "3" "4" "J"])))
    (is (= false (is-hand-two-pairs? true ["2" "5" "3" "4" "J"])))))

(deftest test-is-hand-one-pair
  (testing "is-hand-one-pair? function"
    (is (= (is-hand-one-pair? false ["2" "2" "3" "4" "5"]) true))
    (is (= (is-hand-one-pair? false ["2" "3" "4" "5" "6"]) false))
    (is (= true (is-hand-one-pair? true ["2" "3" "4" "5" "J"])))
    (is (= false (is-hand-one-pair? true ["2" "3" "4" "5" "6"])))))

(deftest test-is-hand-high-card
  (testing "is-hand-high-card? function"
    (is (= true (is-hand-high-card? false ["2" "3" "4" "5" "6"])))
    (is (= false (is-hand-high-card? false ["2" "2" "4" "5" "6"])))
    (is (= true (is-hand-high-card? true ["2" "3" "4" "J" "J"])))
    (is (= false (is-hand-high-card? true ["2" "2" "4" "5" "J"])))))

(deftest test-solution-part1
  (testing "solution-part1 function"
    (is (= (solution-part1 sample-input) 6440))))

(deftest test-solution-part2
  (testing "solution-part2 function"
    (is (= (solution-part2 sample-input) 5905))))