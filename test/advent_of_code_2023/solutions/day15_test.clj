(ns advent-of-code-2023.solutions.day15-test
  (:require [advent-of-code-2023.solutions.day15 :refer :all]
            [clojure.test :refer :all]))

(def test-input "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")
(deftest hash-string-test
  (is (= 0 (hash-string "rn")))
  (is (= 3 (hash-string "pc"))))

(deftest parse-instruction-test
  (is (= {:operation "=", :label "rn", :focal-length 1}
         (parse-instruction "rn=1")))
  (is (= {:operation "-", :label "cm"} (parse-instruction "cm-"))))

(deftest has-same-label?-test
  (let [check-rn (has-same-label? "rn")]
    (is (true? (check-rn ["rn" 1])))
    (is (false? (check-rn ["cm" 1])))))

(deftest place-lens-test
  (is (= [["cm" 4] ["rn" 1]]
         (place-lens [["cm" 4]] {:label "rn", :focal-length 1})))
  (is (= [["rn" 2]] (place-lens [["rn" 1]] {:label "rn", :focal-length 2})))
  (is (= [["rn" 1] ["cm" 2]]
         (place-lens [["rn" 1]] {:label "cm", :focal-length 2}))))

(deftest remove-lens-test
  (is (= [] (remove-lens [["rn" 1]] {:label "rn"})))
  (is (= [] (remove-lens [] {:label "rn"})))
  (is (= [["rn" 1]] (remove-lens [["rn" 1] ["cm" 2]] {:label "cm"})))
  (is (= [["rn" 1] ["cm" 2]] (remove-lens [["rn" 1] ["cm" 2]] {:label "qp"}))))

(deftest apply-instruction-test
  (let [wall (vec (repeat 256 []))
        instructions []]
    (is (every? empty? (apply-all-instructions wall instructions)))))

(deftest calculate-focusing-power-test
  (is (= (calculate-focusing-power 0 [["rn" 1] ["qp" 2]]) [1 4])))

(deftest solution-part1-test (is (= (solution-part1 test-input) 1320)))

(deftest solution-part2-test (is (= (solution-part2 test-input) 145)))


