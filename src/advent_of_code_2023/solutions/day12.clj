(ns advent-of-code-2023.solutions.day12
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string]))

(defn parse-input
  [input]
  (map (fn [[row-str condition-records-str]]
         (let [row (map (fn [c]
                          (case c
                            \? nil
                            \. false
                            \# true))
                     row-str)
               number-records (map #(Integer/parseInt %)
                                (clojure.string/split condition-records-str
                                                      #","))]
           [row number-records]))
    (map #(clojure.string/split % #" ") input)))

(def count-arrangements
  (memoize
    (fn [raw-row number-records]
      ; remove first broken spring as it is irrelevant for calculating
      ; arrangements
      (let [row (if (false? (first raw-row)) (rest raw-row) raw-row)]
        ; if there are no groups, but we still have a working spring, then
        ; the arrangement is invalid otherwise the arrangement is valid
        (cond (empty? number-records) (if (some true? row) 0 1)
              ; if the row is shorter than the total length of group +
              ; gaps, then the arrangement is invalid
              (< (count row)
                 (dec (+ (reduce + number-records) (count number-records))))
                0
              ; if the row is exactly the length of the total length of the
              ; first group and there are broken spring, then the
              ; arrangement is invalid
              (= (count row) (first number-records))
                (if (some false? (take (first number-records) row)) 0 1)
              ; if the row doesn't start with a working spring, then the
              ; arrangement is invalid
              ;otherwise, we can continue calculating the arrangements with
              ;the rest of the row
              :else (+ (if (not= (first row) true)
                         (count-arrangements (rest row) number-records)
                         0)
                       ; if the first group has a broken spring and the
                       ; last spring in group is working, then the
                       ; arrangement is invalid otherwise, we can continue
                       ; calculating the arrangements with the rest of the
                       ; row, skipping the first group
                       (if (and (not (some false?
                                           (take (first number-records) row)))
                                (not= (nth row (first number-records)) true))
                         (count-arrangements (drop (inc (first number-records))
                                                   row)
                                             (rest number-records))
                         0)))))))

(defn solution-part1
  [input]
  (let [parsed-input (parse-input input)]
    (reduce + (map (partial apply count-arrangements) parsed-input))))

(defn repeat-with-separator
  [seq separator times]
  (if (or (empty? seq) (< times 2))
    seq
    (apply concat (interpose [separator] (take times (repeat seq))))))

(defn unfold-row
  [[row number-records]]
  (let [times 5]
    [(repeat-with-separator row nil times)
     (flatten (take times (repeat number-records)))]))

(defn solution-part2
  [input]
  (let [parsed-input (parse-input input)
        unfolded-input (map unfold-row parsed-input)]
    (reduce + (map (partial apply count-arrangements) unfolded-input))))

(defn -main
  []
  (let [input (util/read-input-file "day12.txt")]
    (println "Part 1:" (solution-part1 input))
    (println "Part 2:" (solution-part2 input))))
