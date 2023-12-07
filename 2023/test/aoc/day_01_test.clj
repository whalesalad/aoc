(ns aoc.day-01-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.string :as string]
            [aoc.day-01 :refer [part-1
                                part-2
                                first-digit
                                last-digit
                                integer-from-digits
                                line-to-int]]))

(def test-data
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet")

(def test-data-part-2
  [["two1nine" 29]
   ["eightwothree" 83]
   ["abcone2threexyz" 13]
   ["xtwone3four" 24]
   ["4nineeightseven2" 42]
   ["zoneight234" 14]
   ["7pqrstsixteen" 76]])


(deftest test-first-digit
  (is (= "1" (first-digit "1abc2")))
  (is (= "3" (first-digit "pqr3stu8vwx")))
  (is (= "7" (first-digit "treb7uchet")))
  (is (= "1" (first-digit "two1nine"))))

(deftest test-first-digit-replace
  (is (= "2" (first-digit "two1nine" true)))
  (is (= "8" (first-digit "eightwothree" true)))
  (is (= "1" (first-digit "abcone2threexyz" true)))
  (is (= "2" (first-digit "xtwone3four" true)))
  (is (= "4" (first-digit "4nineeightseven2" true)))
  (is (= "1" (first-digit "zoneight234" true)))
  (is (= "7" (first-digit "7pqrstsixteen" true))))

(deftest test-last-digit
  (is (= "2" (last-digit "1abc2")))
  (is (= "8" (last-digit "pqr3stu8vwx")))
  (is (= "7" (last-digit "treb7uchet"))))

(deftest test-last-digit-replace
  (is (= "9" (last-digit "two1nine" true)))
  (is (= "3" (last-digit "eightwothree" true)))
  (is (= "3" (last-digit "abcone2threexyz" true)))
  (is (= "4" (last-digit "xtwone3four" true)))
  (is (= "2" (last-digit "4nineeightseven2" true)))
  (is (= "4" (last-digit "zoneight234" true)))
  (is (= "6" (last-digit "7pqrstsixteen" true)))
  (is (= "8" (last-digit "abc2x3oneight" true))))


(deftest test-integer-from-digits
  (let [cases [["1" "2" 12]
               ["2" "5" 25]
               ["9" "9" 99]]]
    (doseq [[first second expected] cases]
      (is (= expected
             (integer-from-digits first second))))))

(deftest test-line-to-int-replace
  (doseq [[line expected] test-data-part-2]
    (is (= expected (line-to-int line true)))))

(deftest part1
  (let [expected 142
        actual (part-1 test-data)]
    (is (= expected actual))))

(deftest part2
  (let [test-data (string/join "\n" (map first test-data-part-2))
        actual (part-2 test-data)
        expected 281]
    (is (= expected actual))))
