(ns advent-of-code-2023.solutions.day13-test
  (:require [advent-of-code-2023.solutions.day13 :as day13]
            [advent-of-code-2023.util.grid :as grid]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(def test-input
  "#.##..##.\n..#.##.#.\n##......#\n##......#\n..#.##.#.\n..##..##.\n#.#.##.#.\n\n#...##..#\n#....#..#\n..##..###\n#####.##.\n#####.##.\n..##..###\n#....#..#")

(def inputs (day13/split-grids (str/split-lines test-input)))

(def sample-input-1 (first inputs))
(def sample-input-2 (second inputs))

(deftest split-grids-test
  (testing "split-grids" (is (= inputs [sample-input-1 sample-input-2]))))

(deftest is-true-reflection?-test
  (testing "is-true-reflection?"
    (is (= (day13/is-true-reflection? (grid/pivot-grid sample-input-1) 4)
           false))
    (is (= (day13/is-true-reflection? sample-input-1 2) false))
    (is (= (day13/is-true-reflection? (grid/pivot-grid sample-input-1) 5) true))
    (is (= (day13/is-true-reflection? sample-input-2 4) true))
    (is (= (day13/is-true-reflection? (grid/pivot-grid sample-input-2) 2)
           false))))

(deftest summarize-reflection-test
  (testing "summarize-reflection"
    (is (= (day13/summarize-reflection sample-input-2) 4))
    (is (= (day13/summarize-reflection sample-input-1) 0))
    (is (= (day13/summarize-reflection (grid/pivot-grid sample-input-1)) 5))
    (is (= (day13/summarize-reflection (grid/pivot-grid sample-input-2)) 0))))

(deftest find-horizontal-reflection-test
  (testing "find-horizontal-reflection"
    (println sample-input-1 sample-input-2)
    (is (= (day13/find-horizontal-reflection sample-input-1) 0))
    (is (= (day13/find-horizontal-reflection sample-input-2) 400))))

(deftest find-vertical-reflection-test
  (testing "find-vertical-reflection"
    (is (= (day13/find-vertical-reflection sample-input-1) 5))
    (is (= (day13/find-vertical-reflection sample-input-2) 0))))

(deftest summarize-test
  (testing "summarize"
    (is (= (day13/summarize sample-input-1) 5))
    (is (= (day13/summarize sample-input-2) 400))))

(deftest solution-part1-test
  (testing "solution-part1"
    (is (= (day13/solution-part1 (str/split-lines test-input)) 405))))

(deftest solution-part2-test
  (testing "solution-part2"
    (is (= (day13/solution-part2 (str/split-lines test-input)) 400))))

(run-tests 'advent-of-code-2023.solutions.day13-test)
