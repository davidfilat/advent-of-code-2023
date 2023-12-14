(ns advent-of-code-2023.solutions.day5-test
  (:require [advent-of-code-2023.solutions.day5 :refer :all]
            [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(def sample-input
  (str/split-lines
    "seeds: 79 14 55 13\n\nseed-to-soil map:\n50 98 2\n52 50 48\n\nsoil-to-fertilizer map:\n0 15 37\n37 52 2\n39 0 15\n\nfertilizer-to-water map:\n49 53 8\n0 11 42\n42 0 7\n57 7 4\n\nwater-to-light map:\n88 18 7\n18 25 70\n\nlight-to-temperature map:\n45 77 23\n81 45 19\n68 64 13\n\ntemperature-to-humidity map:\n0 69 1\n1 0 69\n\nhumidity-to-location map:\n60 56 37\n56 93 4"))


(deftest test-parse-seeds
  (testing "Parse seeds"
    (is (= (parse-seeds "seeds: 79 14 55 13") [79 14 55 13]))))

(deftest test-parse-map
  (testing "Parse map"
    (is (= (parse-map "seed-to-soil map:\n50 98 2\n52 50 48")
           [[[98 2] [50 2]] [[50 48] [52 48]]]))))

(deftest test-parse-maps
  (testing "Parse maps"
    (is
      (=
        (parse-maps
          (str/split-lines
            "seed-to-soil map:\n50 98 2\n52 50 48\n\nsoil-to-fertilizer map:\n0 15 37\n37 52 2\n39 0 15"))
        [[[[98 2] [50 2]] [[50 48] [52 48]]]
         [[[15 37] [0 37]] [[52 2] [37 2]] [[0 15] [39 15]]]]))))

(deftest test-parse-input
  (testing "parse-input with sample inputs"
    (is (= (parse-input sample-input)
           {:maps [[[[98 2] [50 2]] [[50 48] [52 48]]]
                   [[[15 37] [0 37]] [[52 2] [37 2]] [[0 15] [39 15]]]
                   [[[53 8] [49 8]] [[11 42] [0 42]] [[0 7] [42 7]]
                    [[7 4] [57 4]]] [[[18 7] [88 7]] [[25 70] [18 70]]]
                   [[[77 23] [45 23]] [[45 19] [81 19]] [[64 13] [68 13]]]
                   [[[69 1] [0 1]] [[0 69] [1 69]]]
                   [[[56 37] [60 37]] [[93 4] [56 4]]]],
            :seeds [79 14 55 13]}))))

(deftest test-map-src-to-dest
  (testing "Map source to destination"
    (is (= (map-src-to-dest [[[98 2] [50 2]] [[50 48] [52 48]]] 99) 51))
    (is (= (map-src-to-dest [[[98 2] [50 2]] [[50 48] [52 48]]] 50) 52))))

(deftest test-pass-through-map
  (testing "Pass through map"
    (is (= (pass-through-map [[[98 2] [50 2]] [[50 48] [52 48]]] [99 100])
           [51 52]))))

(deftest test-pass-through-all-maps
  (testing "Pass through all maps"
    (is (= (pass-through-all-maps [[[[98 2] [50 2]] [[50 48] [52 48]]]
                                   [[[15 37] [0 37]] [[52 2] [37 2]]
                                    [[0 15] [39 15]]]]
                                  [99 100])
           [36 37]))))

(deftest test-solution-part1
  (testing "solution-part1 with sample inputs"
    (with-redefs [util/read-input-file (fn [_] sample-input)]
      (is (= 35 (solution-part1 "day5.txt"))))))

(deftest test-apply-map-to-seed-ranges
  (testing "Apply map to seed ranges"
    (is (= (apply-map-to-seed-ranges [[99 100] [101 102]]
                                     [[[[98 2] [50 2]] [[50 48] [52 48]]]
                                      [[[15 37] [0 37]] [[52 2] [37 2]]
                                       [[0 15] [39 15]]]])
           [[51 52] [53 54]]))))

(deftest test-do-ranges-overlap?
  (testing "Ranges overlap" (is (true? (do-ranges-overlap? [1 3] [3 4]))))
  (testing "Ranges do not overlap"
    (is (false? (do-ranges-overlap? [20 2] [22 1]))))
  (testing "One range is inside the other"
    (is (true? (do-ranges-overlap? [2 4] [1 2]))))
  (testing "Ranges are the same" (is (true? (do-ranges-overlap? [2 4] [2 4])))))

(deftest test-get-range-intersection
  (testing "Ranges overlap" (is (= (get-range-intersection [1 3] [3 4]) [3 1])))
  (testing "Ranges do not overlap"
    (is (= (get-range-intersection [1 2] [4 4]) nil)))
  (testing "One range is inside the other"
    (is (= (get-range-intersection [2 4] [1 2]) [2 1])))
  (testing "Ranges are the same"
    (is (= (get-range-intersection [2 4] [2 4]) [2 4]))))

(deftest test-get-range-difference
  (testing "Ranges overlap" (is (= (get-range-difference [1 3] [3 4]) [[1 2]])))
  (testing "Ranges do not overlap"
    (is (= (get-range-difference [1 2] [4 4]) [[1 2]])))
  (testing "One range is inside the other"
    (is (= (get-range-difference [2 4] [1 2]) [[3 3]])))
  (testing "Ranges are the same"
    (is (= (get-range-difference [2 4] [2 4]) []))))

(deftest test-split-range-into-overlapping-buckets
  (testing "Split range into overlapping buckets"
    (is (= (split-range-into-overlapping-buckets [1 4] [[[3 5]]])
           [[1 2] [3 2]])))
  (testing "No overlap"
    (is (= (split-range-into-overlapping-buckets [1 2] [[[4 4]]]) [[1 2]])))
  (testing "One range is inside the other"
    (is (= (split-range-into-overlapping-buckets [1 9] [[[2 4]]])
           [[1 1] [2 4] [6 4]])))
  (testing "Ranges are the same"
    (is (= (split-range-into-overlapping-buckets [2 4] [[[2 4]]]) [[2 4]]))))

(deftest test-translate-src-range-to-des-range
  (testing "Translate source range to destination range"
    (is (= (translate-src-range-to-dest-range [4 4] [[[4 4] [10 4]]]) [10 4])))
  (testing "No applicable map range"
    (is (= (translate-src-range-to-dest-range [1 2] [[[4 4] [10 4]]]) [1 2])))
  (testing "Source range is in the middle of map source range"
    (is (= (translate-src-range-to-dest-range [5 2] [[[4 4] [12 4]]]) [13 2]))))

(deftest test-translate-src-ranges-to-dest-ranges
  (testing "Translate source ranges to destination ranges"
    (let [src-ranges [[4 4] [10 4] [15 5]]
          map [[[4 4] [10 4]] [[15 5] [25 5]]]]
      (is (= (translate-src-ranges-to-dest-ranges src-ranges map)
             [[10 4] [10 4] [25 5]])))
    (testing "No applicable map range"
      (let [src-ranges [[1 2] [5 2]]
            map [[[4 4] [10 4]]]]
        (is (= (translate-src-ranges-to-dest-ranges src-ranges map)
               [[1 2] [11 2]])))
      (testing "Source range is in the middle of map source range"
        (let [src-ranges [[5 2]]
              map [[[4 4] [10 4]]]]
          (is (= (translate-src-ranges-to-dest-ranges src-ranges map)
                 [[11 2]])))))))

(deftest test-apply-map-to-seed-ranges
  (testing "Apply map to seed ranges"
    (let [seed-ranges [[4 4] [10 4] [15 5] [25 5]]
          maps [[[[4 4] [10 4]] [[15 5] [25 5]]]]]
      (is (= (apply-map-to-seed-ranges seed-ranges maps)
             [[10 4] [10 4] [25 5] [25 5]])))
    (testing "No applicable map range"
      (let [seed-ranges [[1 2] [5 2]]
            maps [[[[4 4] [10 4]]]]]
        (is (= (apply-map-to-seed-ranges seed-ranges maps) [[1 2] [11 2]])))
      (testing "Seed range is in the middle of map source range"
        (let [seed-ranges [[5 2]]
              maps [[[[4 4] [10 4]]]]]
          (is (= (apply-map-to-seed-ranges seed-ranges maps) [[11 2]])))))))

(deftest test-solution-part2
  (testing "solution-part2 with sample inputs"
    (with-redefs [util/read-input-file (fn [_] sample-input)]
      (is (= 46 (solution-part2 "day5.txt"))))))