(ns aoc.day-03)

(def sample-data
  {:part1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
   :part2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"})

(def part-1-re #"(mul\((?<a>\d+),(?<b>\d+)\))")
(def part-2-re #"(mul\((?<a>\d+),(?<b>\d+)\)|((don't)|(do))\(\))")

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

(defn sum-pairs
  [pairs]
  (reduce + (map (fn [[a b]] (* a b)) pairs)))

(defn valid-pairs-part-2
  [data]
  (reduce
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
   data))

(defn part-1
  "Day 03 Part 1"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input-day-1 input)
        output (sum-pairs parsed-input)]
    (println "output:" output)
    output))

(defn part-2
  "Day 03 Part 2"
  [input]
  (println "input:" input)
  (let [parsed-input (parse-input-day-2 input)
        valid-pairs (valid-pairs-part-2 parsed-input)
        output (sum-pairs (:result valid-pairs))]
    (println "output:" output)
    output))