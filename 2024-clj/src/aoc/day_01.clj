(ns aoc.day-01
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

(def sample-data
  "3   4
4   3
2   5
1   3
3   9
3   3")

(defn parse-input
  [input]
  (let [transform-line (fn [line]
              (map (fn [i] (Integer/parseInt (str i))) (str/split line #"\s+")))
        pairs (map transform-line (str/split-lines input))]
    (apply map vector pairs)))

(defn calculate-distances
  [left right]
  (let [left (sort left)
        right (sort right)
        zipped (map vector left right)
        distances (map (fn [[a b]] (abs (- a b))) zipped)]
    (reduce + distances)))

(defn similarity-scores
  [left right]
  (let [freq (frequencies right)
        score (fn [item] (* item (get freq item 0)))
        scores (map score left)]
    (reduce + scores)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input input)
        output (apply calculate-distances parsed-input)]
    (println "output:" output)
    output))

(defn part-2
  "Day 01 Part 2"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input input)
        output (apply similarity-scores parsed-input)]
    (println "output:" output)
    output))