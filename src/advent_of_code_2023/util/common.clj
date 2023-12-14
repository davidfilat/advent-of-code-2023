(ns advent-of-code-2023.util.common
  (:require [clojure.java.io :as io]))

(defn read-input-file
  [filename]
  (with-open [rdr (io/reader (io/resource filename))] (doall (line-seq rdr))))

(defn in-range? [value start end] (and (>= value start) (<= value end)))

(defn find-value [pred coll] (some #(when (pred %) %) coll))