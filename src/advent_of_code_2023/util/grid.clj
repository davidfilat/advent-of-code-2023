(ns advent-of-code-2023.util.grid)



(def directions-map {:north [-1 0], :south [1 0], :west [0 -1], :east [0 1]})


(defn get-next-coordinates
  [direction curr-coordinates]
  (let [[dy dx] (get directions-map direction)
        [y x] curr-coordinates]
    [(+ y dy) (+ x dx)]))


(defn get-in-grid
  "Safely get the element at [row col] in the grid, or nil if out of bounds."
  [grid [row col]]
  (clojure.core/get-in grid [row col] nil))

(defn pivot-grid [grid] (apply mapv vector grid))


