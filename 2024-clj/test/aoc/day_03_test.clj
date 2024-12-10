(ns aoc.day-03-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.string :as string]
            [aoc.day-03 :refer [sample-data
                                sample-data-part-2
                                parse-input-day-1
                                calculate-pairs
                                parse-input-day-2
                                process-part-2]]))


(def test-data (parse-input-day-1 sample-data))


(deftest test-calculate-pairs
  (is (= 161 (calculate-pairs test-data))))


(deftest test-process-part-2
  (let [data (parse-input-day-2 sample-data-part-2)
        processed (process-part-2 data)]
    (is (= 48 processed))))

;; (deftest test-first-digit-replace
;;   (is (= "2" (first-digit "two1nine" true)))
;;   (is (= "8" (first-digit "eightwothree" true)))
;;   (is (= "1" (first-digit "abcone2threexyz" true)))
;;   (is (= "2" (first-digit "xtwone3four" true)))
;;   (is (= "4" (first-digit "4nineeightseven2" true)))
;;   (is (= "1" (first-digit "zoneight234" true)))
;;   (is (= "7" (first-digit "7pqrstsixteen" true))))

;; (deftest test-last-digit
;;   (is (= "2" (last-digit "1abc2")))
;;   (is (= "8" (last-digit "pqr3stu8vwx")))
;;   (is (= "7" (last-digit "treb7uchet"))))

;; (deftest test-last-digit-replace
;;   (is (= "9" (last-digit "two1nine" true)))
;;   (is (= "3" (last-digit "eightwothree" true)))
;;   (is (= "3" (last-digit "abcone2threexyz" true)))
;;   (is (= "4" (last-digit "xtwone3four" true)))
;;   (is (= "2" (last-digit "4nineeightseven2" true)))
;;   (is (= "4" (last-digit "zoneight234" true)))
;;   (is (= "6" (last-digit "7pqrstsixteen" true)))
;;   (is (= "8" (last-digit "abc2x3oneight" true))))


;; (deftest test-integer-from-digits
;;   (let [cases [["1" "2" 12]
;;                ["2" "5" 25]
;;                ["9" "9" 99]]]
;;     (doseq [[first second expected] cases]
;;       (is (= expected
;;              (integer-from-digits first second))))))

;; (deftest test-line-to-int-replace
;;   (doseq [[line expected] test-data-part-2]
;;     (is (= expected (line-to-int line true)))))

;; (deftest part1
;;   (let [expected 142
;;         actual (part-1 test-data)]
;;     (is (= expected actual))))

;; (deftest part2
;;   (let [test-data (string/join "\n" (map first test-data-part-2))
;;         actual (part-2 test-data)
;;         expected 281]
;;     (is (= expected actual))))
