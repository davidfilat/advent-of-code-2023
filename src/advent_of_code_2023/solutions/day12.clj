(ns advent-of-code-2023.solutions.day12
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string]))

(defn parse-input
  [input]
  (map (fn [line]
         (let [[row-str condition-records-str] (clojure.string/split line #" ")
               row (map (fn [c]
                          (case c
                            \? nil
                            \. false
                            \# true))
                        row-str)
               number-records (map #(Integer/parseInt %)
                                   (clojure.string/split condition-records-str
                                                         #","))]
           [row number-records]))
       input))


(defn count-arrangements
  [row number-records]
  (cond (empty? number-records) (if (some true? row) 0 1)
        (< (count row)
           (dec (+ (reduce + number-records) (count number-records))))
        0
        (false? (first row)) (count-arrangements (rest row) number-records)
        (false? (last row)) (count-arrangements (butlast row) number-records)
        (true? (first row))
        (if (or (some false? (take (dec (first number-records)) row))
                (get row (first number-records) false))
          0
          (count-arrangements (drop (inc (first number-records)) row)
                              (rest number-records)))
        :else (+ (count-arrangements (rest row) number-records)
                 (count-arrangements (cons true (rest row)) number-records))))


(defn solution-part1
  [input]
  (let [parsed-input (parse-input input)]
    (reduce + (map (partial apply count-arrangements) parsed-input))))

(defn -main
  []
  (let [input (util/read-input-file "day12.txt")]
    (println "Part 1:" (solution-part1 input))))
