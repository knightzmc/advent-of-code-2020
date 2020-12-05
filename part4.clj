(ns part4
  (:require [clojure.string :as str]))

(defn parse-passport [string]
  (->> (str/split string #"\s+")
       (map #(str/split % #":"))
       (into {})))

(def required-parts #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(defn to-int [s] (try
                   (Integer/parseInt s)
                   (catch NumberFormatException _ nil)))

(defn valid [parts] (every? #(contains? parts %) required-parts))

(def passports (map #(str/replace % "\n" " ") (str/split (slurp "day4.txt") #"\n\n")))

(def passport-parts (map parse-passport passports))

(println "Part 1:" (count (filter valid passport-parts)))

; part 2

(defn valid-height? [height]
  (if (str/ends-with? height "cm")
    (<= 150 (to-int (subs height 0 (- (count height) 2))) 193)
    (if (str/ends-with? height "in")
        (<= 59 (to-int (subs height 0 (- (count height) 2))) 76)
        false)))

(defn part-valid? [[key value]]
  (case key
    "byr" (<= 1920 (to-int value) 2002)
    "iyr" (<= 2010 (to-int value) 2020)
    "eyr" (<= 2020 (to-int value) 2030)
    "hgt" (valid-height? value)
    "hcl" (re-matches #"#[0-9a-f]{6}" value)
    "ecl" (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} value)
    "pid" (and (== (count value) 9) (to-int (str/replace value #"^0+(?!$)" "")))
    "cid" true))

(defn parts-valid? [parts]
  (every? part-valid? parts))

(println "Part 2:" (count (filter (every-pred parts-valid? valid) passport-parts)))