(ns advent-of-code-2023.util
  (:require [clojure.java.io :as io]))

(defn read-input-file [filename]
  (with-open [rdr (io/reader (io/resource filename))]
    (doall (line-seq rdr))))