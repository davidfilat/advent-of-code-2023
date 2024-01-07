(ns advent-of-code-2023.solutions.day12-test
  (:require [advent-of-code-2023.solutions.day12 :refer :all]
            [clojure.test :refer :all]))

(def test-inputs
  (parse-input ["???.### 1,1,3" ".??..??...?##. 1,1,3" "?#?#?#?#?#?#?#? 1,3,1,6"
                "????.#...#... 4,1,1" "????.######..#####. 1,6,5"
                "?###???????? 3,2,1"]))
(deftest test-count-arrangements
  (testing "count-arrangements function"
    (let [expected-outputs [1 4 1 1 4 10]
          actual-outputs (map (partial apply count-arrangements) test-inputs)]
      (is (= expected-outputs actual-outputs)))))

(run-tests 'advent-of-code-2023.solutions.day12-test)
