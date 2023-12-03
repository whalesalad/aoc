(ns aoc.core)

(defn day-to-idx
  [day]
  (if (< day 10) (str "0" day) (str day)))

(defn read-input
  [day part]
  (let [day-idx (day-to-idx day)
        filename (str "resources/day" day-idx "-part" part ".txt")
        data (slurp filename)]
    (println "read input from" filename)
    (if (empty? data)
      nil
      data)))

(defn run-day-part
  [day part]
  (let [day (Integer/parseInt day)
        part (Integer/parseInt part)
        input (or (read-input day part) nil)
        handler-ns (symbol (str "aoc.day-" (day-to-idx day)))]
    (if (nil? input)
      (println "\tno input file found")
      (do
        (require handler-ns)
        (let [part-fn (resolve (symbol (str "aoc.day-" (day-to-idx day) "/part-" part)))]
          (println "\tpart-fn:" part-fn)
          (if (nil? part-fn)
            (println "\tno part function found")
            (println "\tresult:" (part-fn input))))))))

(defn run-day
  [day]
  (let [parts [1 2]]
    (println "running all tasks for day" day)
    (doseq [part parts]
      (run-day-part day part))))

(defn -main
  "Used to dispatch tasks from the command line.

  lein run d01.p1"
  ([day] (run-day day))
  ([day part] (run-day-part day part)))