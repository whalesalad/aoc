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


;; Determine which games would have been possible if the bag had been loaded with only
;; 12 red cubes,
;; 13 green cubes, and
;; 14 blue cubes.
;; What is the sum of the IDs of those games?

(defn part-1
  "Day 02 Part 1
   "
  [input]
  nil)

(defn part-2
  "Day 01 Part 2"
  [input]
  nil)