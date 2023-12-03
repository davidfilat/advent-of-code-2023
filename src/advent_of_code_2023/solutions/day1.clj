(ns advent-of-code-2023.solutions.day1
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]))

(defn keep-only-integers-from-string [s]
  (let [split-string  (str/split s #"[^0-9]")
        non-blank-seq (filter #(not (str/blank? %)) split-string)]
    (str/join "" non-blank-seq)))

(defn merge-numbers [s]
  (Integer/parseInt (str/join "" s)))

(defn get-head-and-last [s]
  [(first s) (last s)])

(defn replace-words-with-digits-sequential [sentence]
  (let [digit-map {"one"  "1" "two" "2" "three" "3" "four" "4"
                   "five" "5" "six" "6" "seven" "7" "eight" "8" "nine" "9"}]
    (reduce (fn [s [k v]] (str/replace s (re-pattern k) v))
            sentence
            digit-map)))

(defn solution-part1 [filename]
  (let [input (util/read-input-file filename)]
    (->> input
         (map keep-only-integers-from-string)
         (map get-head-and-last)
         (map merge-numbers)
         (reduce + 0))))

(defn solution-part2 [filename]
  (let [input (util/read-input-file filename)]
    (->> input
         (map replace-words-with-digits-sequential)
         (map keep-only-integers-from-string)
         (map get-head-and-last)
         (map merge-numbers)
         (reduce + 0))))

(defn -main []
  (println "Part 1: " (solution-part1 "day1.txt"))
  (println "Part 2: " (solution-part2 "day1.txt")))