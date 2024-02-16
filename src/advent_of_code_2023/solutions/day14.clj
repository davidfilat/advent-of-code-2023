(ns advent-of-code-2023.solutions.day14
  (:require [advent-of-code-2023.util.common :as util]
            [advent-of-code-2023.util.grid :as grid]))

(defn parse-input
  [input]
  (->> input
       (mapv (partial mapv
                      #(case %
                         \O true
                         \. nil
                         \# false)))))
(defn rotate-grid
  [direction grid]
  (let [rotate-fn (case direction
                    :north #(mapv reverse (grid/pivot-grid %))
                    :west #(mapv reverse (grid/pivot-grid (mapv reverse %)))
                    :south #(grid/pivot-grid (mapv reverse %))
                    :east grid/pivot-grid)]
    (rotate-fn grid)))

(defn move-stones-to-the-end
  [segment]
  (let [stone true] (sort-by #(if (= % stone) 1 0) segment)))

(defn move-stones-in-column
  [column]
  (let [segments (partition-by false? column)]
    (mapcat move-stones-to-the-end segments)))
(defn move-all-stones [grid] (mapv move-stones-in-column grid))

(defn calculate-load-per-column
  [column]
  (->> column
       (keep-indexed #(when (true? %2) (inc %1)))
       (reduce +)))

(defn solution-part1
  [input]
  (let [grid (parse-input input)]
    (->> grid
         (rotate-grid :north)
         (mapv move-stones-in-column)
         (mapv calculate-load-per-column)
         (reduce +))))

(defn spin-cycle
  [grid]
  (let [rotations [:north :west :south :east]]
    (reduce (fn [acc direction]
              (->> acc
                   (rotate-grid direction)
                   (move-all-stones)))
      grid
      rotations)))

(defn calculate-total-load
  [grid]
  (->> grid
       (rotate-grid :north)
       (mapv calculate-load-per-column)
       (reduce +)))

(defn solution-part2
  [input]
  (let [grid (parse-input input)
        target-cycles 1e9
        sample-loads (map calculate-total-load
                       (take 200 (iterate spin-cycle grid)))
        cycle-length (count (distinct sample-loads))
        offset (mod target-cycles cycle-length)]
    (nth sample-loads (+ cycle-length offset))))

(defn -main
  []
  (let [input (util/read-input-file "day14.txt")]
    (println "Part 1:" (solution-part1 input))
    (println "Part 2:" (solution-part2 input))))
