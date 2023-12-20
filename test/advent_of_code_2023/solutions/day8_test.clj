(ns advent-of-code-2023.solutions.day8-test
  (:require [advent-of-code-2023.solutions.day8 :as day8]
            [clojure.test :refer :all]))
(deftest test-convert-directions-to-keys
  (testing "Converts directions to keys"
    (is (= [:left :left :right] (day8/convert-directions-to-keys "LLR")))))

(deftest test-parse-node
  (testing "Parses node string"
    (is (= ["AAA" "BBB" "BBB"] (day8/parse-node "AAA = (BBB, BBB)")))))

(deftest test-parse-map
  (testing "Parses map"
    (is (= {"AAA" {:left "BBB", :right "BBB"},
            "BBB" {:left "AAA", :right "ZZZ"},
            "ZZZ" {:left "ZZZ", :right "ZZZ"}}
           (day8/parse-map ["AAA = (BBB, BBB)" "BBB = (AAA, ZZZ)"
                            "ZZZ = (ZZZ, ZZZ)"])))))

(deftest test-parse-input
  (testing "Parses input"
    (is (= (day8/parse-input ["LLR" "AAA = (BBB, BBB)" "BBB = (AAA, ZZZ)"
                              "ZZZ = (ZZZ, ZZZ)"])
           {:directions [:left :left :right],
            :nodes-map {"AAA" {:left "BBB", :right "BBB"},
                        "BBB" {:left "AAA", :right "ZZZ"},
                        "ZZZ" {:left "ZZZ", :right "ZZZ"}}}))))

(deftest test-follow-directions-to
  (testing "Follows directions to destination"
    (let [directions (cycle [:left :left :right])
          destination "ZZZ"
          map {"AAA" {:left "BBB", :right "BBB"},
               "BBB" {:left "AAA", :right "ZZZ"},
               "ZZZ" {:left "ZZZ", :right "ZZZ"}}]
      (is (= 6 (day8/follow-directions-to destination directions map))))))

(deftest test-solution-part1
  (testing "Solution for part 1"
    (is (= (day8/solution-part1 ["LLR" "" "AAA = (BBB, BBB)" "BBB = (AAA, ZZZ)"
                                 "ZZZ = (ZZZ, ZZZ)"]
                                "ZZZ")
           6))))

(deftest test-follow-directions-to-ghost-version
  (testing "Follows directions to ghost version"
    (let [directions [:left :right]
          map {"CCA" {:left "DDB", :right "XXX"},
               "DDB" {:left "XXX", :right "11Z"},
               "11Z" {:left "DDB", :right "XXX"},
               "YYA" {:left "ZZB", :right "XXX"},
               "ZZB" {:left "22C", :right "22C"},
               "22C" {:left "22Z", :right "22Z"},
               "22Z" {:left "ZZB", :right "ZZB"},
               "XXX" {:left "XXX", :right "XXX"}}]
      (is (= (day8/follow-directions-to-ghost-version (cycle directions) map)
             6)))))

(deftest test-solution-part2
  (testing "Solution for part 2"
    (is (= (day8/solution-part2 ["LR" "" "CCA = (DDB, XXX)" "DDB = (XXX, AAZ)"
                                 "AAZ = (DDB, XXX)" "YYA = (ZZB, XXX)"
                                 "ZZB = (YYC, YYC)" "YYC = (BBZ, BBZ)"
                                 "BBZ = (ZZB, ZZB)" "XXX = (XXX, XXX)"])
           6))))