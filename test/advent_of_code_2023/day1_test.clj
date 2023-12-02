(ns advent-of-code-2023.day1-test
  (:require [advent-of-code-2023.day1 :refer :all]
            [advent-of-code-2023.util :as util]
            [clojure.test :refer :all]))

(deftest test-keep-only-integers-from-string
  (testing "Only integers are kept from string"
    (is (= "12345" (keep-only-integers-from-string "abc12345def")))
    (is (= "6789" (keep-only-integers-from-string "6789")))
    (is (= "" (keep-only-integers-from-string "abcdef")))))

(deftest test-merge-numbers
  (testing "Merge numbers into one integer"
    (is (= 123 (merge-numbers ["1" "2" "3"])))))

(deftest test-get-head-and-last
  (testing "Get first and last elements of a sequence"
    (is (= ["1" "5"] (get-head-and-last ["1" "2" "3" "4" "5"])))
    (is (= ["a" "a"] (get-head-and-last ["a"])))))

(deftest test-replace-words-with-digits-sequential
  (testing "Replace words with digits sequentially"
    (is (= "123" (replace-words-with-digits-sequential "onetwothree")))
    (is (= "456" (replace-words-with-digits-sequential "fourfivesix")))))

(deftest test-solution-part-1
  (with-redefs [util/read-input-file (fn [_] ["fivepqxlpninevh2xxsnsgg63pbvdnqptmg" "eight8zlctbmsixhrvbpjb84nnmlcqkzrsix" "ddgjgcrssevensix37twooneightgt"])]
    (let [result          (solution-part1 "fake-filename")
          expected-result 144]
      (is (= result expected-result)))))

(deftest test-solution-part-2
  (with-redefs [util/read-input-file (fn [_] ["fivepqxlpninevh2xxsnsgg63pbvdnqptmg" "eight8zlctbmsixhrvbpjb84nnmlcqkzrsix" "ddgjgcrssevensix37twooneightgt"])]
    (let [result          (solution-part2 "fake-filename")
          expected-result 217]
      (is (= result expected-result)))))

(run-tests)
