(ns aoc.day-01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.day-01 :refer [part-1
                                part-2
                                first-digit
                                last-digit
                                integer-from-digits
                                replace-spelled-integers-in-line
                                replace-spelled-integer]]))

(def test-data
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet")

(deftest test-first-digit
  (is (= "1" (first-digit "1abc2")))
  (is (= "3" (first-digit "pqr3stu8vwx")))
  (is (= "7" (first-digit "treb7uchet"))))


(deftest test-last-digit
  (is (= "2" (last-digit "1abc2")))
  (is (= "8" (last-digit "pqr3stu8vwx")))
  (is (= "7" (last-digit "treb7uchet"))))


(deftest test-integer-from-digits
  (let [cases [["1" "2" 12]
               ["2" "5" 25]
               ["9" "9" 99]]]
    (doseq [[first second expected] cases]
      (is (= expected
             (integer-from-digits first second))))))


(deftest test-replace-spelled-integer
  (is (= "11" (replace-spelled-integer "one1" "one")))
  (is (= "2222three" (replace-spelled-integer "two2two2three" "two"))))


(deftest test-replace-spelled-numbers
  (is (= "219" (replace-spelled-integers-in-line "two1nine")))
  (is (= "823" (replace-spelled-integers-in-line "eightwothree"))))


(deftest part1
  (let [expected 142
        actual (part-1 test-data)]
    (is (= expected actual))))

;; (deftest part2
;;   (let [expected nil]
;;     (is (= expected (part-2 (slurp (resource "day-01-example.txt")))))))
