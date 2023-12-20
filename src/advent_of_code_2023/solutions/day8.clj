(ns advent-of-code-2023.solutions.day8
  (:require [advent-of-code-2023.util.common :as util]
            [advent-of-code-2023.util.math :refer :all]
            [clojure.string :as str]))

(defn convert-directions-to-keys
  [directions]
  (map #(if (= % "L") :left :right) (str/split directions #"")))

(defn parse-node [node-str] (re-seq #"[A-Z]{3}" node-str))

(defn parse-map
  [raw-nodes-seq]
  (reduce (fn [map raw-node]
            (->> raw-node
                 (parse-node)
                 (apply #(assoc map %1 {:left %2, :right %3}))))
    {}
    raw-nodes-seq))

(defn parse-input
  [input]
  (let [directions (first input)
        nodes-map (filter (comp not empty?) (rest input))]
    {:directions (convert-directions-to-keys directions),
     :nodes-map (parse-map nodes-map)}))

(defn follow-directions-to
  [destination directions map]
  (loop [i 0
         curr-node "AAA"]
    (if (= curr-node destination)
      i
      (recur (inc i) ((nth directions i) (get map curr-node))))))

(defn solution-part1
  [input destination]
  (let [{:keys [directions nodes-map]} (parse-input input)]
    (follow-directions-to destination (cycle directions) nodes-map)))

(defn count-steps-to-ghost-destination
  [start directions map]
  (loop [i 0
         curr-node start]
    (if (str/ends-with? curr-node "Z")
      i
      (recur (inc i) ((nth directions i) (get map curr-node))))))

(defn follow-directions-to-ghost-version
  [directions map]
  (let [starts (filter #(str/ends-with? % "A") (keys map))]
    (reduce lcm
      (mapv (comp bigint
                  #(count-steps-to-ghost-destination % (cycle directions) map))
        starts))))

(defn solution-part2
  [input]
  (let [{:keys [directions nodes-map]} (parse-input input)]
    (follow-directions-to-ghost-version directions nodes-map)))

(defn -main
  []
  (let [input (util/read-input-file "day8.txt")]
    (println "Part 1: " (solution-part1 input "ZZZ"))
    (println "Part 2: " (solution-part2 input))))