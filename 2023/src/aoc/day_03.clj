(ns aoc.day-03
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]
            [clojure.spec.alpha :as spec]
            [clojure.set]))

;; {:symbol "*", :start 3, :end 4, :line 1}
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
                     :end (+ -1 end)
                     :line idx})]
    (map transform matches)))

(defn parse-line-for-symbols
  [line idx]
  (let [matches (re-seq-pos #"([^\.^\d])" line)
        transform (fn [{start :start end :end symbol :group}]
                    {:symbol symbol
                     :start start
                     :end (+ -1 end)
                     :line idx})]
    (map transform matches)))

(defn parse-schematic
  [schematic]
  (let [lines (map str/trim (str/split-lines schematic))
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


;; {:number 467, :start 0, :end 3, :line 0}
;; {:symbol "*", :start 3, :end 4, :line 1}
(def n {:number 467, :start 0, :end 3, :line 0})
(def s {:symbol "*", :start 3, :end 4, :line 1})

;; (defn test-symbol-number
;;   [symbol number]
;;   (let [start-idx (+ -1 (:start symbol))
;;         end-idx (+ 2 (:end symbol))]
;;     (some? [(spec/int-in-range? start-idx end-idx (:start number))
;;             (spec/int-in-range? start-idx end-idx (:end number))])))

(defn test-symbol-number
  [symbol number]
  (let [symbol-range (set (range (+ -1 (:start symbol))
                                 (+ 1 (:end symbol))))
        number-range (set (range (+ -1 (:start number))
                                 (+ 1 (:end number))))
        intersection (clojure.set/intersection symbol-range number-range)]
    (println {:symbol-range symbol-range :number-range number-range :intersection intersection})
    (not-empty intersection)))

(defn numbers-adjacent-to-symbol-line
  [symbol numbers]
  (let [line (:line symbol)
        start-line (+ -1 line)
        end-line (+ 2 line)
        numbers-in-range (filter (fn [{line :line}]
                                   (spec/int-in-range? start-line end-line line))
                                 numbers)]
    (pp/pprint {:symbol symbol :line line :start-line start-line :end-line end-line})
    (filter (fn [number] (test-symbol-number symbol number))
            (distinct numbers-in-range))))

(defn find-numbers-with-adjacent-symbols
  [schematic]
  (let [symbols (:symbols schematic)
        numbers (:numbers schematic)]
    (distinct (flatten (map (fn [s]
                              (numbers-adjacent-to-symbol-line s numbers))
                            symbols)))))

(defn find-gears-for-symbol
  [symbol numbers]
  (let [numbers-for-symbol (numbers-adjacent-to-symbol-line symbol numbers)
        just-numbers (map :number numbers-for-symbol)]
    (if (= 2 (count just-numbers))
      (reduce * just-numbers)
      nil)))

(defn find-gears
  [schematic]
  (let [symbols (:symbols schematic)
        numbers (:numbers schematic)
        gears (map (fn [s]
                     (find-gears-for-symbol s numbers))
                   symbols)]
    (distinct (flatten (remove nil? gears)))))

(defn part-1
  "Day 03 Part 1"
  [input]
  (let [schematic (parse-schematic input)
        good-numbers (find-numbers-with-adjacent-symbols schematic)
        just-numbers (map :number good-numbers)
        sum (reduce + just-numbers)]
    sum))

(defn part-2
  "Day 03 Part 2"
  [input]
  (let [schematic (parse-schematic input)
        gears (find-gears schematic)]
    (reduce + gears)))