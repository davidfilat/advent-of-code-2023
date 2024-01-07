(ns advent-of-code-2023.solutions.day10
  (:require [advent-of-code-2023.util.common :as util]
            [advent-of-code-2023.util.grid :as grid]
            [clojure.string :as str])
  (:import (clojure.lang Keyword)
           (java.util List)))

(def junctions-map
  {"L" {:south :east, :west :north},
   "J" {:south :west, :east :north},
   "7" {:north :west, :east :south},
   "F" {:north :east, :west :south}})

(def direction-pipe-map
  {:north ["|" "F" "7"],
   :south ["|" "J" "L"],
   :east ["J" "7" "-"],
   :west ["F" "L" "-"]})

(defn parse-input [^List raw-input] (vec (mapv #(str/split % #"") raw-input)))

(defn find-start-coordinates
  [^List input]
  (let [y (util/find-index #(.contains % "S") input)
        x (.indexOf (get input y) "S")]
    [y x]))

(defn get-pipe-at-coordinates [map coordinates] (get-in map coordinates nil))

(defn is-junction? [pipe] (contains? junctions-map pipe))

(defn get-next-direction
  [^String pipe ^Keyword direction]
  (if (is-junction? pipe) (direction (get junctions-map pipe)) direction))

(defn navigate-pipes
  [^List map ^List start-coordinates ^Keyword begin-direction]
  (loop [curr-coordinates start-coordinates
         direction begin-direction
         path []]
    (let [curr-pipe (get-pipe-at-coordinates map curr-coordinates)
          next-direction (get-next-direction curr-pipe direction)
          next-coordinates (grid/get-next-coordinates next-direction
                                                      curr-coordinates)]
      (cond (or (= curr-pipe ".")
                (nil? next-direction)
                (nil? curr-pipe)
                (contains? (get direction-pipe-map direction) curr-pipe))
              nil
            (= (get-pipe-at-coordinates map next-coordinates) "S")
              (conj path curr-coordinates)
            :else (recur next-coordinates
                         next-direction
                         (conj path curr-coordinates))))))
(defn navigate-all-direction
  [^List start-coordinates ^List map]
  (let [directions [:east :west :north :south]]
    (some (fn [direction] (navigate-pipes map start-coordinates direction))
          directions)))

(defn solution-part1
  [^List raw-input]
  (let [grid (parse-input raw-input)
        start-coordinates (find-start-coordinates grid)]
    (->> grid
         (navigate-all-direction start-coordinates)
         (count)
         (max)
         (#(/ % 2)))))

(defn solution-part2 [raw-input] "To be implemented...")

(defn -main
  []
  (let [input (util/read-input-file "day10.txt")]
    (println "Part 1:" (solution-part1 input))
    (println "Part 2:" (solution-part2 input))))
