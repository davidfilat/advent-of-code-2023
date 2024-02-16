(ns advent-of-code-2023.solutions.day15
  (:require [advent-of-code-2023.util.common :as util]
            [clojure.string :as str]))

(defn hash-string
  [string]
  (reduce (fn [acc c] (mod (* 17 (+ acc (int c))) 256)) 0 string))

(defn solution-part1
  [input]
  (->> (str/split input #",")
       (mapv str/trim)
       (mapv hash-string)
       (reduce +)))

(defn parse-instruction
  [instruction-str]
  (if (str/includes? instruction-str "=")
    (let [[label focal-length] (str/split instruction-str #"=")]
      {:operation "=",
       :label label,
       :focal-length (Integer/parseInt focal-length)})
    (let [label (apply str
                  (take (dec (count instruction-str)) instruction-str))]
      {:operation "-", :label label})))

(defn has-same-label? [label] (fn [[old-label]] (= old-label label)))

(defn place-lens
  [box instruction]
  (let [{:keys [label focal-length]} instruction
        lens-index (util/find-index (has-same-label? label) box)
        new-lens [label focal-length]]
    (if (nil? lens-index) (conj box new-lens) (assoc box lens-index new-lens))))

(defn remove-lens
  [box instruction]
  (let [{:keys [label]} instruction
        lens-index (util/find-index (has-same-label? label) box)]
    (if (nil? lens-index)
      box
      (->> box
           (filter (complement (has-same-label? label)))
           (vec)))))

(defn apply-instruction
  [wall instruction]
  (let [{:keys [operation label]} instruction]
    (vec (for [box-number (range (count wall))]
           (let [box (get wall box-number)]
             (if (= box-number (hash-string label))
               (cond (= operation "=") (place-lens box instruction)
                     (= operation "-") (remove-lens box instruction)
                     :else (throw (Exception. "Unsupported operation")))
               box))))))
(defn apply-all-instructions
  [wall instructions]
  (reduce apply-instruction wall instructions))

(defn calculate-focusing-power
  [box-number box]
  (map-indexed (fn [idx [_ focal-length]]
                 (* (inc box-number) (inc idx) focal-length))
               box))

(defn solution-part2
  [input]
  (let [wall (vec (repeat 256 []))]
    (->> (str/split input #",")
         (mapv str/trim)
         (map parse-instruction)
         (apply-all-instructions wall)
         (map-indexed calculate-focusing-power)
         (flatten)
         (reduce +))))
(defn -main
  []
  (let [input (apply str (util/read-input-file "day15.txt"))]
    (println "Part 1:" (solution-part1 input))
    (println "Part 2:" (solution-part2 input))))


