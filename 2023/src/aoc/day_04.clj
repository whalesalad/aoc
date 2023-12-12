(ns aoc.day-04
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]
            [clojure.spec.alpha :as spec]
            [clojure.set]))

(def card-1
  "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")

(def example
  "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
   Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
   Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
   Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
   Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
   Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")

(defn extract-numbers
  [s]
  (set (map #(Integer/parseInt %) (re-seq #"\d+" s))))

(defn score-matching-numbers
  [num-matching]
  (reduce (fn [x y] (* 2 x))
          1
          (range (+ -1 num-matching))))

(defn parse-line
  [line]
  (let [parts (map str/trim (str/split line #"[:\|]"))
        card (second (re-find #"Card (\d+)" (first parts)))
        winning-numbers (extract-numbers (nth parts 1))
        our-numbers (extract-numbers (nth parts 2))
        matching-numbers (clojure.set/intersection winning-numbers our-numbers)
        num-matching (count matching-numbers)]
    {:card card
     :winning winning-numbers
     :ours our-numbers
     :matching matching-numbers
     :num-matching num-matching
     :score (if (> num-matching 0)
              (score-matching-numbers num-matching)
              0)}))

(defn part-1
  "Day 04 Part 1"
  [input]
  (let [raw-lines (map str/trim (str/split-lines input))
        lines (map parse-line raw-lines)
        scores (map :score lines)]
    (reduce + scores)))

(defn part-2
  "Day 04 Part 2"
  [input]
  (let [raw-lines (map str/trim (str/split-lines input))
        lines (map parse-line raw-lines)
        by-card (into {} (map (fn [line] [(:card line) line]) lines))]
    (pp/pprint by-card)))