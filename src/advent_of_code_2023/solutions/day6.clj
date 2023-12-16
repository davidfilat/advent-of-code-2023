(ns advent-of-code-2023.solutions.day6
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]))

(defn parse-input
  [input]
  (->> input
       (map #(str/split % #"\s+"))
       (map #(map read-string (rest %)))))

(defn calculate-distance
  [^Long charging-time ^Long total-time]
  (* charging-time (- total-time charging-time)))

(defn calculate-distances-for-each-charging-time-limit
  [total-time]
  (map (fn [charging-time] (calculate-distance charging-time total-time))
    (range 0 (+ total-time 1))))

(defn get-winning-charging-times
  ^Long [charging-time-limit record-distance]
  (let [distances (calculate-distances-for-each-charging-time-limit
                    charging-time-limit)]
    (filter #(< record-distance %) distances)))

(defn solution-part1
  [input]
  (->> input
       (parse-input)
       (apply map vector)
       (map (partial apply get-winning-charging-times))
       (map count)
       (reduce *)))

(defn solution-part2
  [input]
  (->> input
       (parse-input)
       (map #(str/join "" %))
       (map read-string)
       (apply get-winning-charging-times)
       (count)))

(defn -main
  []
  (let [input (util/read-input-file "day6.txt")]
    (println "Part 1: " (solution-part1 input))
    (println "Part 2: " (solution-part2 input))))