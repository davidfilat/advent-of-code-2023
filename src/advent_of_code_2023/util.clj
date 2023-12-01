(ns advent-of-code-2023.util)
(defn read-input [filename]
  (->> (slurp (str "../../inputs/" filename))
       (clojure.string/split-lines)
       ))