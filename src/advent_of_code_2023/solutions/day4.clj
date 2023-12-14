(ns advent-of-code-2023.solutions.day4
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-numbers
  [sl]
  (->> sl
       (#(str/split % #" "))
       (filter #(not (str/blank? %)))
       (map read-string)))

(defn parse-card-line
  [line]
  (let [pattern #"Card\s+(\d+): ([\d\s]+) \| ([\d\s]+)"
        matcher (re-matcher pattern line)]
    (if (re-find matcher)
      {:card-number (Integer/parseInt (second (re-matches pattern line))),
       :winning-numbers (parse-numbers (nth (re-matches pattern line) 2)),
       :my-numbers (parse-numbers (nth (re-matches pattern line) 3)),
       :copies 1})))

(defn get-intersection
  [card]
  (set/intersection (set (:winning-numbers card)) (set (:my-numbers card))))

(defn get-score
  [my-winning-numbers]
  (int (Math/pow 2 (dec (count my-winning-numbers)))))

(defn get-card-score
  [card]
  (->> card
       (get-intersection)
       (get-score)))

(defn solution-part1
  [filename]
  (let [input (util/read-input-file filename)]
    (->> input
         (map parse-card-line)
         (map get-card-score)
         (reduce + 0))))

(defn count-winning-numbers
  [card]
  (->> card
       (get-intersection)
       (count)))

(defn process-card
  [card-stack card-index]
  (let [card (nth card-stack card-index)
        card-score (count-winning-numbers card)
        card-number (:card-number card)
        card-copies (:copies card)]
    (if (zero? card-score)
      card-stack
      (map (fn [curr-card]
             (if (and (> (:card-number curr-card) card-number)
                      (<= (:card-number curr-card) (+ card-number card-score)))
               (update curr-card :copies #(+ card-copies %))
               curr-card))
        card-stack))))

(defn process-card-stack
  [card-stack]
  (reduce process-card card-stack (range (count card-stack))))

(defn solution-part2
  [filename]
  (let [input (util/read-input-file filename)]
    (->> input
         (map parse-card-line)
         (process-card-stack)
         (map :copies)
         (reduce +))))
(defn -main
  []
  (println "Part 1: " (solution-part1 "day4.txt"))
  (println "Part 2: " (solution-part2 "day4.txt")))
