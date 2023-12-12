(ns aoc.day-03
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

(def test-schematic
  "467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..")

(defn re-seq-pos [pattern string]
  (let [m (re-matcher pattern string)]
    ((fn step []
       (when (. m find)
         (cons {:start (. m start) :end (. m end) :group (. m group)}
               (lazy-seq (step))))))))

;; {:height 10,
;;  :width 10,
;;  :numbers
;;  ({:number 467, :start 0, :end 3, :line 0}
;;   {:number 114, :start 5, :end 8, :line 0}
;;   {:number 35, :start 2, :end 4, :line 2}
;;   {:number 633, :start 6, :end 9, :line 2}
;;   {:number 617, :start 0, :end 3, :line 4}
;;   {:number 58, :start 7, :end 9, :line 5}
;;   {:number 592, :start 2, :end 5, :line 6}
;;   {:number 755, :start 6, :end 9, :line 7}
;;   {:number 664, :start 1, :end 4, :line 9}
;;   {:number 598, :start 5, :end 8, :line 9}),
;;  :symbols
;;  ({:symbol "*", :start 3, :end 4, :line 1}
;;   {:symbol "#", :start 6, :end 7, :line 3}
;;   {:symbol "*", :start 3, :end 4, :line 4}
;;   {:symbol "+", :start 5, :end 6, :line 5}
;;   {:symbol "$", :start 3, :end 4, :line 8}
;;   {:symbol "*", :start 5, :end 6, :line 8})}

(defn parse-line-for-numbers
  [line idx]
  (let [matches (re-seq-pos #"(\d+)" line)
        transform (fn [{start :start end :end number :group}]
                    {:number (Integer/parseInt number)
                     :start start
                     :end end
                     :line idx})]
    (map transform matches)))

(defn parse-line-for-symbols
  [line idx]
  (let [matches (re-seq-pos #"([*#+$])" line)
        transform (fn [{start :start end :end symbol :group}]
                    {:symbol symbol
                     :start start
                     :end end
                     :line idx})]
    (map transform matches)))

(defn parse-schematic
  [schematic]
  (let [lines (str/split-lines schematic)
        height (count lines)
        width (count (first lines))
        numbers (concat (map-indexed (fn [idx line]
                                       (parse-line-for-numbers line idx)) lines))
        symbols (concat (map-indexed (fn [idx line]
                                       (parse-line-for-symbols line idx)) lines))]
    {:height height
     :width width
     :numbers (flatten numbers)
     :symbols (flatten symbols)}))

(defn part-1
  "Day 03 Part 1"
  [input]
  nil)

(defn part-2
  "Day 03 Part 2"
  [input]
  nil)