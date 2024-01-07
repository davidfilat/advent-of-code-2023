(ns advent-of-code-2023.util.common-test
  (:require [advent-of-code-2023.util.common :refer :all]
            [clojure.test :refer :all]))

(deftest test-in-range?
  (testing "Value is in range" (is (true? (in-range? 5 1 10))))
  (testing "Value is at the start of the range" (is (true? (in-range? 1 1 10))))
  (testing "Value is at the end of the range" (is (true? (in-range? 10 1 10))))
  (testing "Value is below the range" (is (false? (in-range? 0 1 10))))
  (testing "Value is above the range" (is (false? (in-range? 11 1 10)))))

(deftest test-find-value
  (testing "Find first even number" (is (= (find-value even? [1 2 3 4 5]) 2)))
  (testing "Find first number greater than 10"
    (is (= (find-value #(> % 10) [1 2 3 4 5 11]) 11)))
  (testing "No value satisfies the predicate"
    (is (nil? (find-value #(> % 10) [1 2 3 4 5])))))

(deftest test-between?
  (testing "between? function"
    (is (true? (between? 5 1 10)))
    (is (true? (between? 1 1 10)))
    (is (true? (between? 10 1 10)))
    (is (false? (between? 0 1 10)))
    (is (false? (between? 11 1 10)))))

(deftest test-find-index
  (testing "Find index of first even number"
    (is (= (find-index even? [1 2 3 4 5]) 1)))
  (testing "Find index of first number greater than 10"
    (is (= (find-index #(> % 10) [1 2 3 4 5 11]) 5)))
  (testing "No index satisfies the predicate"
    (is (nil? (find-index #(> % 10) [1 2 3 4 5])))))
