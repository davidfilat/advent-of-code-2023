(ns advent-of-code-2023.solutions.day2
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]))

(defn parse-round
  [round]
  (let [entries (str/split round #", ")]
    (into {}
          (map (fn [entry]
                 (let [[number color] (str/split entry #" ")]
                   {color (Integer/parseInt number)}))
            entries))))

(defn parse-game-data
  [data-str]
  (let [game-number (re-find #"\d+" data-str)
        rounds (str/split (str/replace data-str #"Game \d+: " "") #"; ")]
    {:game-number (Integer/parseInt game-number),
     :rounds (map parse-round rounds)}))

(defn get-max-number-of-marbles-for-each-color
  [game]
  (let [colors '("red" "blue" "green")]
    (into {}
          (map (fn [color]
                 (let [rounds (get game :rounds)
                       color-rounds-numbers (map #(get % color 0) rounds)]
                   [color (reduce max color-rounds-numbers)]))
            colors))))

(defn all-keys-greater?
  [map1 map2]
  (every? (fn [key] (>= (get map1 key 0) (get map2 key 0))) (keys map1)))

(defn solution-part1
  [filename]
  (let [input (util/read-input-file filename)
        available-marbles {"red" 12, "green" 13, "blue" 14}]
    (->> input
         (map parse-game-data)
         (map #(assoc %
                 :max-marbles (get-max-number-of-marbles-for-each-color %)))
         (filter #(all-keys-greater? available-marbles (:max-marbles %)))
         (map :game-number)
         (reduce + 0))))

(defn solution-part2
  [filename]
  (let [input (util/read-input-file filename)]
    (->> input
         (map parse-game-data)
         (map get-max-number-of-marbles-for-each-color)
         (map vals)
         (map (partial filter #(> % 0)))
         (map #(reduce * 1 %))
         (reduce + 0))))

(defn -main
  []
  (println "Part 1: " (solution-part1 "day2.txt"))
  (println "Part 2: " (solution-part2 "day2.txt")))
