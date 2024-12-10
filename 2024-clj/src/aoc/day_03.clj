(ns aoc.day-03
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

(def sample-data
  "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(def sample-data-part-2
  "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(def part-1-re #"(mul\((?<a>\d+),(?<b>\d+)\))")
(def part-2-re #"(mul\((?<a>\d+),(?<b>\d+)\)|((don't)|(do))\(\))")

(defn str-to-int
  [str]
  (Integer. str))

(defn extract-pair
  [row]
  (mapv #(Integer. %) (take-last 2 (filter some? row))))

(defn parse-input-day-1
  [input]
  (let [matches (re-seq part-1-re input)
        extract-pairs (fn [data] (map extract-pair data))]
    (extract-pairs matches)))

(defn parse-input-day-2
  [input]
  (let [matches (re-seq part-2-re input)]
    matches))

(defn calculate-pairs
  [pairs]
  (reduce + (map (fn [[a b]] (* a b)) pairs)))

(defn part-1
  "Day 03 Part 1"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input-day-1 input)
        output (calculate-pairs parsed-input)]
    (println "output:" output)
    output))


(defn process-part-2
  [data]
  (let [result (reduce
                (fn [acc row]
                  (let [{:keys [mode result]} acc
                        first-item (first row)]
                    (cond
                      ;; switch to drop mode on "don't()"
                      (= first-item "don't()") {:mode :drop :result result}

                      ;; switch back to capture mode on "do()"
                      (= first-item "do()") {:mode :capture :result result}

                      ;; capture pairs in capture mode
                      (and (= mode :capture) (re-find #"mul" first-item))
                      {:mode :capture :result (conj result (extract-pair row))}

                      ;; else, continue with current mode
                      :else acc)))
                ;; initial state
                {:mode :capture :result []}
                ;; input data
                data)]
    (calculate-pairs (:result result))))

(defn part-2
  "Day 03 Part 2"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input-day-2 input)
        output (process-part-2 parsed-input)]
    (println "output:" output)
    output))