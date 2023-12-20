(ns advent-of-code-2023.core
  (:require [advent-of-code-2023.solutions.day1 :as day1]
            [advent-of-code-2023.solutions.day2 :as day2]
            [advent-of-code-2023.solutions.day3 :as day3]
            [advent-of-code-2023.solutions.day4 :as day4]
            [advent-of-code-2023.solutions.day5 :as day5]
            [advent-of-code-2023.solutions.day6 :as day6]
            [advent-of-code-2023.solutions.day7 :as day7]
            [advent-of-code-2023.solutions.day8 :as day8]))

(defn -main
  "Execute all solutions."
  []
  (println "Day 1:")
  (day1/-main)
  (println "Day 2:")
  (day2/-main)
  (println "Day 3:")
  (day3/-main)
  (println "Day 4:")
  (day4/-main)
  (println "Day 5:")
  (day5/-main)
  (println "Day 6")
  (day6/-main)
  (println "Day 7")
  (day7/-main)
  (println "Day 8")
  (day8/-main))
