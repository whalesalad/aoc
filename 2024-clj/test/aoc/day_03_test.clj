(ns aoc.day-03-test
  (:require [clojure.test :refer [deftest is]]
            [aoc.day-03 :refer [sample-data
                                parse-input-day-1
                                parse-input-day-2
                                sum-pairs
                                valid-pairs-part-2]]))



(deftest test-sum-pairs
  (let [data (parse-input-day-1 (:part1 sample-data))]
    (is (= 161 (sum-pairs data)))))


(deftest test-part-2
  (let [data (parse-input-day-2 (:part2 sample-data))
        processed (:result (valid-pairs-part-2 data))]
    (is (= 48 (sum-pairs processed)))))