(ns aoc.day-02
  (:require [clojure.string :as str]
            [clojure.pprint :as pp]))

(def prompt-data
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(def prompt-games
  (str/split-lines prompt-data))

(defn parse-game
  "turn 'Game 1' into 1"
  [game-str]
  (->> game-str
       (re-find #"Game (\d+)")
       (last)
       (Integer/parseInt)))

(defn parse-color
  [color-str]
  (let [[num-blocks color] (str/split color-str #" ")]
    (hash-map (keyword color)
              (Integer/parseInt num-blocks))))

(defn parse-turn
  "transform '3 blue, 4 red' into {:blue 3 :red 4}"
  [turn-str]
  (let [color-groups (str/split turn-str #", ")]
    (into (sorted-map) (map parse-color color-groups))))

(defn parse-game-line
  [line]
  (let [[game & turns] (map str/trim (str/split line #"(:|;)"))]
    {:game (parse-game game)
     :turns (map parse-turn turns)}))

(def games
  (map parse-game-line prompt-games))

(defn turns-to-maximums
  [turns]
  "given a collection of turns, identify the max per-color"
  (reduce
   (fn [result color-map]
     (merge-with max result color-map))
   {}
   turns))

(defn check-game
  "figure out the maximums for the game. then use the test map to compare each key (red, green, blue)
   to determine if the game was possible. if yes, return the game idx, if no, return nil"
  [{game :game turns :turns} test]
  (let [{max-red :red max-blue :blue max-green :green} (turns-to-maximums turns)
        result (if (every? true? [(<= max-red (:red test))
                                  (<= max-blue (:blue test))
                                  (<= max-green (:green test))])
                 game
                 nil)]
    (println "game:" game (if result "PASS" "FAIL"))
    (pp/pprint turns)
    (println "\t" "red:" max-red "blue:" max-blue "green:" max-green)
    (println)
    result))

(defn powers-for-game
  [{turns :turns}]
    (reduce * (vals (turns-to-maximums turns))))

(defn games-possible-for
  [games bag-limits]
  (let [pred (fn [g] (check-game g bag-limits))]
    (filter pred games)))

;; Determine which games would have been possible if the bag had been loaded with only
;;   12 red cubes,
;;   13 green cubes, and
;;   14 blue cubes.
;;   What is the sum of the IDs of those games?

(defn test-game
  [game]
  (println "testing game:" game)
  (check-game game {:red 12, :green 13, :blue 14}))

(comment (games-possible-for games {:red 12 :green 13 :blue 14}))

(defn part-1
  "Day 02 Part 1"
  [input]
  (let [bag-limits {:red 12, :green 13, :blue 14}
        games (map parse-game-line (str/split-lines input))
        possible-games (games-possible-for games bag-limits)]
  (reduce + (map :game possible-games))))

(defn part-2
  "Day 01 Part 2"
  [input]
  (let [games (map parse-game-line (str/split-lines input))
        powers (map powers-for-game games)]
    (reduce + powers)))