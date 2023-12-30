(ns advent-of-code-2023.solutions.day11
  (:require [advent-of-code-2023.util.common :as util]
            [advent-of-code-2023.util.grid :as grid]
            [clojure.math.combinatorics :as combo])
  (:import (java.util List)))

(defn parse-input [input] (mapv #(mapv char %) input))




(defn find-empty-rows
  ^List [map]
  (reduce (fn [acc row-index]
            (let [row (get map row-index)]
              (if (every? #(= \. %) row) (conj acc row-index) acc)))
          []
          (range (count map))))


(defn find-empty-columns [map] (find-empty-rows (grid/pivot-grid map)))



(defn find-all-galaxies-in-universe
  [universe-map]
  (mapcat identity
          (map-indexed (fn [i row]
                         (reduce (fn [acc j]
                                   (if (= \# (get row j)) (conj acc [i j]) acc))
                                 []
                                 (range (count row))))
                       universe-map)))

(def mem-find-empty-rows (memoize find-empty-rows))
(def mem-find-empty-columns (memoize find-empty-columns))


(defn get-distance
  [universe-map empty-space-expansion-coef axis-fn find-empty-fn point1 point2]
  (let [[p1 p2] (sort (map axis-fn [point1 point2]))
        distance (- p2 p1)
        empty-spaces-to-traverse (filter #(util/between? % p1 p2)
                                         (find-empty-fn universe-map))]
    (+ (* empty-space-expansion-coef (count empty-spaces-to-traverse))
       (- distance (count empty-spaces-to-traverse)))))

(defn get-y-distance
  [universe-map empty-space-expansion-coef point1 point2]
  (get-distance universe-map
                empty-space-expansion-coef
                first
                mem-find-empty-rows
                point1
                point2
                ))

(defn get-x-distance
  [universe-map empty-space-expansion-coef point1 point2]
  (get-distance universe-map
                empty-space-expansion-coef
                second
                mem-find-empty-columns
                point1
                point2
                ))

(defn find-shortest-path-length-between-galaxies
  [universe-map empty-space-expansion-coef galaxy1 galaxy2]
  (->> [galaxy1 galaxy2]
       ((juxt
          #(apply get-y-distance universe-map empty-space-expansion-coef %)
          #(apply get-x-distance universe-map empty-space-expansion-coef %)))
       (apply +)))

(defn solution-part1
  [input]
  (let [universe-map (parse-input input)]
    (->> universe-map
         (find-all-galaxies-in-universe)
         (#(combo/combinations % 2))
         (map
           #(apply find-shortest-path-length-between-galaxies universe-map 2 %))
         (reduce + 0))))


(defn solution-part2
  [input]
  (let [universe-map (parse-input input)]
    (->> universe-map
         (find-all-galaxies-in-universe)
         (#(combo/combinations % 2))
         (map #(apply find-shortest-path-length-between-galaxies
                      universe-map
                      1000000
                      %))
         (reduce + 0))))
(defn -main
  []
  (let [raw-input (util/read-input-file "day11.txt")]
    (println "Part 1: " (solution-part1 raw-input))
    (println "Part 2: " (solution-part2 raw-input))))

