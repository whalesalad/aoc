(ns aoc.core)

(defn day-to-idx
  [day]
  (if (< day 10) (str "0" day) (str day)))

(def indent "  ")

(defn read-input
  [day]
  (let [day-idx (day-to-idx day)
        ;; todo if no input is found for part 2, fall-back to part 1
        ;; so far all of the part 2's rely on the same input.
        filename (str "resources/day" day-idx ".txt")
        data (slurp filename)]
    (println "reading input from" filename)
    (if (empty? data)
      nil
      data)))

(defn profile-fn [f & args]
  (let [start-time (System/nanoTime)
        start-mem (.totalMemory (Runtime/getRuntime))
        result (apply f args)
        end-mem (.totalMemory (Runtime/getRuntime))
        end-time (System/nanoTime)
        exec-time (/ (- end-time start-time) 1e6)
        memory-used (- end-mem start-mem)]
    ;; (println "Execution time:"  "ms")
    ;; (println "Memory used:"  "bytes")
    {:result result
     :exec-time exec-time
     :memory-used memory-used}))

(defn to-int
  [value]
  (cond
    (string? value) (Integer/parseInt value)
    :else value))

(defn run-day-part
  [day part]
  (let [day (to-int day)
        part (to-int part)
        input (or (read-input day) nil)
        handler-ns (symbol (str "aoc.day-" (day-to-idx day)))]
    (println "running day" day "part" part)
    (if (nil? input)
      (println (str indent "no input file found"))
      (do
        (require handler-ns)
        (let [part-fn (resolve (symbol (str "aoc.day-" (day-to-idx day) "/part-" part)))]
          (println indent "part-fn:" part-fn)
          (if (nil? part-fn)
            (println indent "no part function found")
            (let [result-with-profiling (profile-fn part-fn input)
                  result (:result result-with-profiling)]
              (println indent "took:" (:exec-time result-with-profiling) "ms")
              (println indent "result:" result))))))))

(defn run-day
  [day]
  (let [parts [1 2]]
    (println "running all tasks for day" day)
    (doseq [part parts]
      ;; (println "part:" part "type: " (type part))
      (run-day-part day part))))

(defn -main
  "Used to dispatch tasks from the command line.

  lein run d01.p1"
  ([day] (run-day day))
  ([day part] (run-day-part day part)))