(ns advent-of-code-2023.solutions.day16
  (:require [clojure.core.match :refer [match]]))

(def mirror-types [\\ \/ \- \|])

(def directions [:north :east :south :west])
(defn mirror->direction
  [from-direction mirror]
  (match [from-direction mirror]
         [_ \\] [(get {:south :west, :west :south, :east :north, :north :east}
                      from-direction)]
         [_ \/] [(get {:south :east, :east :south, :west :north, :north :west}
                      from-direction)]
         [(:or :west :east) \|] [:north :south]
         [(:or :south :north) \-] [:east :west]
         :else [from-direction]))

(defn get-next-coordinates
  [direction [y x]]
  (let [directions-map {:north [-1 0], :south [1 0], :west [0 -1], :east [0 1]}
        [dy dx] (get directions-map direction)]
    [(+ y dy) (+ x dx)]))

