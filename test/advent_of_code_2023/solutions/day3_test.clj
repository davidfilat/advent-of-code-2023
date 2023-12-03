(ns advent-of-code-2023.solutions.day3-test
  (:require [advent-of-code-2023.solutions.day3 :refer :all]
            [advent-of-code-2023.util.common :as util]
            [clojure.test :refer :all]))

(def test-grid
  ["467..114.."
   "...*......"
   "..35..633."
   "......#..."
   "617*......"
   ".....+.58."
   "..592....."
   "......755."
   "...$.*...."
   ".664.598.."])

(deftest extract-numbers-test
  (is (= (extract-numbers test-grid)
         [{:end-index 2 :row 0 :start-index 0 :value 467}
          {:end-index 7 :row 0 :start-index 5 :value 114}
          {:end-index 3 :row 2 :start-index 2 :value 35}
          {:end-index 8 :row 2 :start-index 6 :value 633}
          {:end-index 2 :row 4 :start-index 0 :value 617}
          {:end-index 8 :row 5 :start-index 7 :value 58}
          {:end-index 4 :row 6 :start-index 2 :value 592}
          {:end-index 8 :row 7 :start-index 6 :value 755}
          {:end-index 3 :row 9 :start-index 1 :value 664}
          {:end-index 7 :row 9 :start-index 5 :value 598}])))

(deftest extract-symbols-test
  (is (= (extract-symbols test-grid)
         [{:index 3 :row 1 :value \*}
          {:index 6 :row 3 :value \#}
          {:index 3 :row 4 :value \*}
          {:index 5 :row 5 :value \+}
          {:index 3 :row 8 :value \$}
          {:index 5 :row 8 :value \*}])))

(deftest is-adjacent?-test
  (let [number   {:end-index 6 :row 0 :start-index 4 :value 114}
        asterisk {:index 3 :row 1 :value \*}
        plus     {:value \+ :row 5 :index 6}]
    (is (is-adjacent? number asterisk))
    (is (not (is-adjacent? number plus)))))

(deftest get-adjacent-numbers-test
  (let [asterisk {:value *, :row 1, :index 3}
        numbers  (extract-numbers test-grid)]
    (is (= (get-adjacent-numbers asterisk numbers)
           [{:end-index 2 :row 0 :start-index 0 :value 467}
            {:end-index 3 :row 2 :start-index 2 :value 35}]))))

(deftest is-part-number?-test
  (let [number       {:end-index 2 :row 0 :start-index 0 :value 467}
        wrong-number {:end-index 7 :row 0 :start-index 5 :value 114}
        symbols      (extract-symbols test-grid)]
    (is (is-part-number? number symbols))
    (is (not (is-part-number? wrong-number symbols)))))

(deftest calculate-ratio-for-asterisk-test
  (let [asterisk {:value *, :row 1, :index 3}
        numbers  (extract-numbers test-grid)]
    (is (= (calculate-ratio-for-asterisk asterisk numbers) 16345))))

(deftest solution-part1-test
  (with-redefs [util/read-input-file (fn [_] test-grid)]
    (let [result          (solution-part1 "fake-filename")
          expected-result 4361]
      (is (= result expected-result)))))

(deftest solution-part1-test
  (with-redefs [util/read-input-file (fn [_] test-grid)]
    (let [result          (solution-part2 "fake-filename")
          expected-result 467835]
      (is (= result expected-result)))))
