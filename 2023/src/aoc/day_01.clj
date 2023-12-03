(ns aoc.day-01
  (:require [clojure.string :as string]))

(def replacements
  {"zero" "0"
   "one" "1"
   "two" "2"
   "three" "3"
   "four" "4"
   "five" "5"
   "six" "6"
   "seven" "7"
   "eight" "8"
   "nine" "9"})

(def part-1-re #"\d")
(def part-2-re #"\d|one|two|three|four|five|six|seven|eight|nine|ten")

(defn first-digit
  "Find the first digit in a string of text"
  [text]
  (let [digit (first (re-seq part-2-re text))]
    (if-let [replacement (get replacements digit)]
      replacement
      digit)))

(defn last-digit
  "Find the last digit in a string of text"
  [text]
  (first (reverse (re-seq part-2-re text))))

(defn integer-from-digits
  [first last]
  (Integer/parseInt (str first last)))

(defn line-to-int
  [line]
  (let [first (first-digit line)
        last (last-digit line)]
    (integer-from-digits first last)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (println "input:" input)
  (let [lines (string/split-lines input)
        integers (map line-to-int lines)]
    (println "integers:" integers)
    (reduce + integers)))

(defn part-2
  "Day 01 Part 2"
  [input]
  nil)
