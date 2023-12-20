(ns advent-of-code-2023.solutions.day9
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]))

(defn parse-input
  [input]
  (->> input
       (map #(str/split % #" "))
       (map #(mapv read-string %))))

(defn calculate-differences
  [numbers]
  (let [shifted-numbers (rest numbers)
        zipped-numbers (mapv vector shifted-numbers numbers)]
    (mapv (partial apply -) zipped-numbers)))

(defn calculate-all-differences
  [records]
  (loop [i 0
         differences []
         numbers records]
    (let [difference (calculate-differences numbers)]
      (if (every? (partial = 0) numbers)
        differences
        (recur (inc i) (conj differences difference) difference)))))

(defn calculate-next-number
  [differences]
  (reduce (fn [intermediate-value difference-nums]
            (+ intermediate-value (last difference-nums)))
    0
    (rseq differences)))

(defn solution-part1
  [report]
  (->> report
       (mapv (juxt identity calculate-all-differences))
       (map #(apply cons %))
       (map vec)
       (map calculate-next-number)
       (reduce +)))

(defn calculate-prev-number
  [differences]
  (reduce (fn [intermediate-value difference-nums]
            (if (nil? intermediate-value)
              (first difference-nums)
              (- (first difference-nums) intermediate-value)))
    nil
    (rseq differences)))

(defn solution-part2
  [report]
  (->> report
       (mapv (juxt identity calculate-all-differences))
       (map #(apply cons %))
       (map vec)
       (map calculate-prev-number)
       (reduce +)))

(defn -main
  []
  (let [input (parse-input (util/read-input-file "day9.txt"))]
    (println "Part 1: " (solution-part1 input))
    (println "Part 2: " (solution-part2 input))))
