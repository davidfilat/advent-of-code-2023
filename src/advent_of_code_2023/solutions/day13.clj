(ns advent-of-code-2023.solutions.day13
  (:require [advent-of-code-2023.util.common :as util]
            [advent-of-code-2023.util.grid :refer [pivot-grid]]))

(defn split-grids
  [input]
  (mapv vec (filter #(> (count %) 1) (vec (partition-by empty? input)))))

(defn is-true-reflection?
  ([grid reflection-line] (is-true-reflection? grid 0 reflection-line))
  ([grid smudge reflection-line]
   (let [count-mismatches (fn [[a b]] (count (filter false? (map = a b))))
         above (subvec grid 0 reflection-line)
         below (subvec grid reflection-line)
         pairs (map vector (reverse above) below)]
     (->> pairs
          (map count-mismatches)
          (reduce +)
          (= smudge)))))

(defn summarize-reflection
  ([grid] (summarize-reflection grid 0))
  ([grid smudge]
   (or (first (filter (partial is-true-reflection? grid smudge)
                (range 1 (count grid))))
       0)))

(defn find-horizontal-reflection
  ([grid] (find-horizontal-reflection grid 0))
  ([grid smudge] (* 100 (summarize-reflection grid smudge))))

(defn find-vertical-reflection
  ([grid] (find-vertical-reflection grid 0))
  ([grid smudge] (summarize-reflection (pivot-grid grid) smudge)))

(defn summarize
  ([grid] (summarize 0 grid))
  ([smudge grid]
   (let [h-score (find-horizontal-reflection grid smudge)
         v-score (find-vertical-reflection grid smudge)]
     (+ h-score v-score))))

(defn solution-part1
  [input]
  (let [grids (split-grids input)] (reduce + (map (partial summarize) grids))))

(defn solution-part2
  [input]
  (let [grids (split-grids input)]
    (reduce + (map (partial summarize 1) grids))))

(defn -main
  []
  (let [input (util/read-input-file "day13.txt")]
    (println "Part 1:" (solution-part1 input))
    (println "Part 2:" (solution-part2 input))))
