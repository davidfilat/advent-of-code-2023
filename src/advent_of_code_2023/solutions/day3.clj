(ns advent-of-code-2023.solutions.day3
  (:require [advent-of-code-2023.util.common :as util]))

(defn extract-numbers
  [grid]
  (mapcat (fn [row row-index]
            (let [matcher (re-matcher #"\d+" row)]
              (loop [matches []]
                (if (.find matcher)
                  (recur (conj matches
                               {:value (Integer/parseInt (.group matcher)),
                                :row row-index,
                                :start-index (.start matcher),
                                :end-index (+ (.start matcher)
                                              (dec (count (.group matcher))))}))
                  matches))))
    grid
    (range)))

(defn extract-symbols
  [grid]
  (mapcat (fn [row index]
            (keep-indexed (fn [idx char]
                            (when (re-matches #"[^\d\.]" (str char))
                              {:value char, :row index, :index idx}))
                          row))
    grid
    (range)))

(defn in-same-row?
  [num-row ast-row num-end ast-index num-start]
  (and (= num-row ast-row)
       (or (= num-end (dec ast-index)) (= num-start (inc ast-index)))))

(defn directly-above-below?
  [num-row ast-row num-start ast-index num-end]
  (and (or (= ast-row (dec num-row)) (= ast-row (inc num-row)))
       (<= num-start ast-index num-end)))

(defn diagonally-adjacent?
  [num-row ast-row num-start ast-index num-end]
  (and (or (= ast-row (dec num-row)) (= ast-row (inc num-row)))
       (or (= ast-index (dec num-start)) (= ast-index (inc num-end)))))

(defn is-adjacent?
  [number symbol]
  (let [num-row (:row number)
        num-start (:start-index number)
        num-end (:end-index number)
        sym-row (:row symbol)
        sym-index (:index symbol)]
    (or (in-same-row? num-row sym-row num-end sym-index num-start)
        (directly-above-below? num-row sym-row num-start sym-index num-end)
        (diagonally-adjacent? num-row sym-row num-start sym-index num-end))))

(defn get-adjacent-numbers
  [symbol numbers]
  (filter #(is-adjacent? % symbol) numbers))

(defn is-part-number? [number symbols] (some #(is-adjacent? number %) symbols))

(defn solution-part1
  [filename]
  (let [grid (util/read-input-file filename)
        numbers (extract-numbers grid)
        symbol-coords (extract-symbols grid)]
    (->> numbers
         (filter #(is-part-number? % symbol-coords))
         (map :value)
         (reduce + 0))))

(defn calculate-ratio-for-asterisk
  [asterisk numbers]
  (let [adjacent-numbers (get-adjacent-numbers asterisk numbers)]
    (when (= 2 (count adjacent-numbers))
      (reduce * (map :value adjacent-numbers)))))

(defn solution-part2
  [filename]
  (let [grid (util/read-input-file filename)
        numbers (extract-numbers grid)
        symbol-coords (extract-symbols grid)
        asterisks (filter #(= (:value %) \*) symbol-coords)]
    (->> asterisks
         (map #(calculate-ratio-for-asterisk % numbers))
         (remove nil?)
         (reduce + 0))))

(defn -main
  []
  (println "Part 1: " (solution-part1 "day3.txt"))
  (println "Part 2: " (solution-part2 "day3.txt")))
