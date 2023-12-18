(ns advent-of-code-2023.solutions.day7
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.core :refer :all]
            [clojure.string :as str])
  (:import (java.util List)))
(defn get-card-strength
  ^Integer [^Boolean is-joker-wildcard? ^String card]
  (let [card-strength-map
          (if is-joker-wildcard? "J23456789TQKA" "23456789TJQKA")]
    (str/index-of card-strength-map card)))

(defn compare-card-strength
  [^Boolean is-joker-wildcard? ^String card1 ^String card2]
  (if (= card1 card2)
    0
    (if (> (get-card-strength is-joker-wildcard? card1)
           (get-card-strength is-joker-wildcard? card2))
      1
      -1)))

(defn compare-hand-strength
  ^Integer [^Boolean is-joker-wildcard? ^List hand1 ^List hand2]
  (let [hand1-first-card (first hand1)
        hand2-first-card (first hand2)
        is-hand1-stronger (compare-card-strength is-joker-wildcard?
                                                 hand1-first-card
                                                 hand2-first-card)]
    (if (= is-hand1-stronger 0)
      (if (empty? (rest hand1))
        0
        (compare-hand-strength is-joker-wildcard? (rest hand1) (rest hand2)))
      is-hand1-stronger)))

(defn parse-frequencies
  ^List [^List hand]
  (->> hand
       frequencies
       ((juxt identity (comp sort vals) #(apply max (vals %))))))


(defn is-hand-five-of-a-kind?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies _ max-freq] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)
        non-joker-frequencies (vals (dissoc frequencies "J"))]
    (or (= max-freq 5)
        (and is-joker-wildcard?
             (= (apply max non-joker-frequencies) (- 5 number-of-jokers))))))

(defn is-hand-four-of-a-kind?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies _ max-freq] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)
        non-joker-frequencies (vals (dissoc frequencies "J"))]
    (or (= max-freq 4)
        (and is-joker-wildcard?
             (= (apply max non-joker-frequencies) (- 4 number-of-jokers))))))

(defn is-hand-full-house?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies freq-vals _] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)
        non-joker-frequencies (dissoc frequencies "J")
        non-joker-freq-vals (sort (vals non-joker-frequencies))]
    (or (= freq-vals [2 3])
        (and is-joker-wildcard?
             (or (and (= number-of-jokers 1)
                      (or (= non-joker-freq-vals [1 3])
                          (= non-joker-freq-vals [2 2])))
                 (and (= number-of-jokers 2) (= non-joker-freq-vals [1 2])))))))

(defn is-hand-three-of-a-kind?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies _ max-freq] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)
        non-joker-frequencies (vals (dissoc frequencies "J"))]
    (or (= max-freq 3)
        (and is-joker-wildcard?
             (= (apply max non-joker-frequencies) (- 3 number-of-jokers))))))

(defn is-hand-two-pairs?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies freq-vals _] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)
        non-joker-frequency-vals (sort (vals (dissoc frequencies "J")))]
    (or (= freq-vals [1 2 2])
        (and is-joker-wildcard?
             (or (and (= number-of-jokers 1)
                      (= non-joker-frequency-vals [1 1 2]))
                 (>= number-of-jokers 2))))))

(defn is-hand-one-pair?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies freq-vals max-freq] (parse-frequencies hand)
        number-of-jokers (get frequencies "J" 0)]
    (or (= (sort freq-vals) [1 1 1 2])
        (and is-joker-wildcard? (= max-freq 1) (= number-of-jokers 1)))))

(defn is-hand-high-card?
  ^Boolean [^Boolean is-joker-wildcard? ^List hand]
  (let [[frequencies _ max-freq] (parse-frequencies hand)
        non-joker-frequencies (vals (dissoc frequencies "J"))]
    (if is-joker-wildcard?
      (or (empty? non-joker-frequencies)
          (= (apply max non-joker-frequencies) 1))
      (= max-freq 1))))

(def hand-type-functions
  [is-hand-five-of-a-kind? is-hand-four-of-a-kind? is-hand-full-house?
   is-hand-three-of-a-kind? is-hand-two-pairs? is-hand-one-pair?
   is-hand-high-card?])

(defn compare-hands
  [is-joker-wildcard? hand1 hand2]
  (let [hand-compare-fns (if is-joker-wildcard?
                           (concat hand-type-functions [is-hand-high-card?])
                           hand-type-functions)]
    (loop [fns hand-compare-fns]
      (let [fn (partial (first fns) is-joker-wildcard?)
            hand1-result (fn hand1)
            hand2-result (fn hand2)]
        (cond (and hand1-result (not hand2-result)) 1
              (and (not hand1-result) hand2-result) -1
              (and hand1-result hand2-result)
                (compare-hand-strength is-joker-wildcard? hand1 hand2)
              :else (recur (rest fns)))))))

(defn sort-deck
  ^List [^Boolean is-joker-wildcard? ^List deck]
  (sort (fn [[hand1 _] [hand2 _]]
          (compare-hands is-joker-wildcard? hand1 hand2))
        deck))

(defn parse-input
  [input]
  (->> input
       (map #(str/split % #"\s+"))
       (map (fn [[a b]] [(str/split a #"") (read-string b)]))))

(defn solution-part1
  [input]
  (->> input
       (parse-input)
       (sort-deck false)
       (map second)
       (map-indexed (fn [index bidding] (* bidding (inc index))))
       (reduce + 0)))

(defn solution-part2
  [input]
  (->> input
       (parse-input)
       (sort-deck true)
       (map second)
       (map-indexed (fn [index bidding] (* bidding (inc index))))
       (reduce + 0)))

(defn -main
  []
  (let [input (util/read-input-file "day7.txt")]
    (println "Part 1: " (solution-part1 input))
    (println "Part 2: " (solution-part2 input))))