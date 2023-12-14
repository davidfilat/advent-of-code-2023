(ns advent-of-code-2023.solutions.day5
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str])
  (:import (java.util List Map)))

(defn parse-seeds
  ^List [^String seeds-str]
  (->> seeds-str
       (#(str/replace % "seeds: " ""))
       (#(str/split % #" "))
       (filter #(not (str/blank? %)))
       (map read-string)
       (filter number?)))

(defn reformat-ranges
  ^List [^List map-entry]
  (let [[dest-start src-start length] map-entry]
    [[src-start length] [dest-start length]]))

(defn parse-map
  ^List [^String raw]
  (let [lines (str/split (str/trim raw) #"\n")
        map-lines (rest lines)]
    (->> map-lines
         (map #(str/split % #" "))
         (mapv (partial mapv read-string))
         (mapv reformat-ranges))))

(defn parse-maps
  ^List [^String maps-str]
  (->> maps-str
       (#(str/join "\n" %))
       (#(str/split % #"\n\n"))
       (map parse-map)))

(defn parse-input
  ^Map [^List input]
  {:seeds (parse-seeds (first input)), :maps (parse-maps (rest input))})

(defn map-src-to-dest
  ^Long [^List map ^Long src-value]
  (let [range (util/find-value
                (fn [[[src-start src-length] _]]
                  (util/in-range? src-value src-start (+ src-start src-length)))
                map)]
    (if range
      (let [[[src-start _] [dest-start _]] range]
        (+ dest-start (- src-value src-start)))
      src-value)))

(defn pass-through-map
  ^List [^List curr-map ^List seed]
  (map (fn [^Long value] (map-src-to-dest curr-map value)) seed))

(defn pass-through-all-maps
  ^List [^List maps ^List seeds]
  (reduce (fn [^List src ^List map] (pass-through-map map src)) seeds maps))

(defn solution-part1
  ^Long [^String filename]
  (let [{:keys [seeds maps]} (parse-input (util/read-input-file filename))]
    (reduce min (pass-through-all-maps maps seeds))))

(defn do-ranges-overlap?
  [^List range1 ^List range2]
  (let [[range1-start range1-length] range1
        [range2-start range2-length] range2]
    (or (and (<= range1-start range2-start)
             (< range2-start (+ range1-start range1-length)))
        (and (<= range2-start range1-start)
             (< range1-start (+ range2-start range2-length))))))

(defn get-range-intersection
  [^List seed-range ^List map-range]
  (let [[range1-start range1-length] seed-range
        [range2-start range2-length] map-range
        intersection-start (max range1-start range2-start)
        intersection-end (min (+ range1-start range1-length)
                              (+ range2-start range2-length))]
    (if (>= intersection-start intersection-end)
      nil
      [intersection-start (- intersection-end intersection-start)])))

(defn get-range-difference
  [^List seed-range ^List map-range]
  (let [intersection (get-range-intersection seed-range map-range)]
    (if intersection
      (let [[range1-start range1-length] seed-range
            [intersection-start intersection-length] intersection
            left-diff (when (< range1-start intersection-start)
                        [range1-start (- intersection-start range1-start)])
            right-diff (when (> (+ range1-start range1-length)
                                (+ intersection-start intersection-length))
                         [(+ intersection-start intersection-length)
                          (- (+ range1-start range1-length)
                             (+ intersection-start intersection-length))])]
        (remove nil? [left-diff right-diff]))
      [seed-range])))

(defn find-applicable-map-range
  ^List [^List seed-range ^List map]
  (util/find-value (fn [[src_range _]]
                     (do-ranges-overlap? seed-range src_range))
                   map))
(defn split-range-into-overlapping-buckets
  ^List [^List seed-range ^List map]
  (let [[first-overlapping-src-range _] (find-applicable-map-range seed-range
                                                                   map)]
    (if first-overlapping-src-range
      (let [intersection (get-range-intersection seed-range
                                                 first-overlapping-src-range)
            differences (get-range-difference seed-range
                                              first-overlapping-src-range)]
        (sort-by
          first
          (cons intersection
                (mapcat (fn [difference]
                          (split-range-into-overlapping-buckets difference map))
                  differences))))
      [seed-range])))

(defn translate-src-range-to-dest-range
  ^List [^List src-range ^List map]
  (let [first-applicable-range (find-applicable-map-range src-range map)]
    (if first-applicable-range
      (let [[src-start src-length] src-range
            [[map-src-start _] [map-dest-start _]] first-applicable-range
            offset (- src-start map-src-start)
            dest-start (+ map-dest-start offset)]
        [dest-start src-length])
      src-range)))

(defn translate-src-ranges-to-dest-ranges
  ^List [^List src-ranges ^List map]
  (->> src-ranges
       (mapcat #(split-range-into-overlapping-buckets % map))
       (mapv #(translate-src-range-to-dest-range % map))))

(defn apply-map-to-seed-ranges
  ^List [^List seed-ranges ^List maps]
  (reduce (fn [src-ranges map]
            (translate-src-ranges-to-dest-ranges src-ranges map))
    seed-ranges
    maps))

(defn solution-part2
  ^Long [filename]
  (let [{:keys [seeds maps]} (parse-input (util/read-input-file filename))
        seed-ranges (partition 2 seeds)]
    (->> (apply-map-to-seed-ranges seed-ranges maps)
         (map first)
         (reduce min))))

(defn -main
  []
  (let [filename "day5.txt"]
    (println "Part 1: " (solution-part1 filename))
    (println "Part 2: " (solution-part2 filename))))
