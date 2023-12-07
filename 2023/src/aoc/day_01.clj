(ns aoc.day-01
  (:require [clojure.string :as string]
            [clojure.pprint :as pp]))

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
(def part-2-re #"(?=(\d|one|two|three|four|five|six|seven|eight|nine|ten))")

(defn get-digit
  "Find the first digit in a string of text"
  [text replace-words? getter]
  (let [re (if replace-words? part-2-re part-1-re)
        matches (map last (re-seq re text))
        match (str (getter matches))]
    (if replace-words?
      (get replacements match match)
      match)))

(defn first-digit
  "Find the first digit in a string of text"
  ([text] (first-digit text false))
  ([text replace-words?] (get-digit text replace-words? first)))

(defn last-digit
  "Find the last digit in a string of text"
  ([text] (last-digit text false))
  ([text replace-words?] (get-digit text replace-words? last)))

(defn integer-from-digits
  [first last]
  (Integer/parseInt (str first last)))

(defn line-to-int
  [line replace-words?]
  (let [first (first-digit line replace-words?)
        last (last-digit line replace-words?)]
    (integer-from-digits first last)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (println "input:" input)
  (let [lines (string/split-lines input)
        to-int (fn [line] (line-to-int line false))
        integers (map to-int lines)]
    (println "integers:" integers)
    (reduce + integers)))

(defn part-2
  "Day 01 Part 2"
  [input]
  (println "input:" input)
  (let [lines (string/split-lines input)
        to-int (fn [line] (line-to-int line true))
        integers (map to-int lines)]
    (pp/pprint (zipmap lines integers))
    (reduce + integers)))