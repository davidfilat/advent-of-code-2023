(ns advent-of-code-2023.util.grid)



(def directions-map {:north [-1 0], :south [1 0], :west [0 -1], :east [0 1]})


(defn get-next-coordinates
  [direction curr-coordinates]
  (let [[dy dx] (get directions-map direction)
        [y x] curr-coordinates]
    (println direction curr-coordinates)
    [(+ y dy) (+ x dx)]))


(defn get-in-grid
  "Safely get the element at [row col] in the grid, or nil if out of bounds."
  [grid [row col]]
  (when (and (>= row 0)
             (< row (count grid))
             (>= col 0)
             (< col (count (first grid))))
    (clojure.core/get-in grid [row col])))
