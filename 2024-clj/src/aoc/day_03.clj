(ns aoc.day-03
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

(def sample-data
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(def part-1-re #"(mul\((?<a>\d+),(?<b>\d+)\))")

(defn str-to-int
  [str]
  (Integer. str))

(defn parse-input
  [input]
  (let [matches (re-seq part-1-re input)
        extract-pairs (fn [data] (map #(mapv str-to-int (take-last 2 %)) data))]
    (extract-pairs matches)))

(defn calculate-pairs
  [pairs]
  (reduce + (map (fn [[a b]] (* a b)) pairs)))

(defn part-1
  "Day 03 Part 1"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input input)
        output (calculate-pairs parsed-input)]
    (println "output:" output)
    output))

;; (defn part-2
;;   "Day 01 Part 2"
;;   [input]
;;   ;; (println "input:" input)
;;   (let [parsed-input (parse-input input)
;;         output (apply similarity-scores parsed-input)]
;;     ;; (println "output:" output)
;;     output))