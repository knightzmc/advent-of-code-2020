(ns part4
  (:require [clojure.string :as str]))

(defn parse-passport [string]
  (->> (str/split string #"\s+")
       (map #(str/split % #":"))
       (into {})))

(def required-parts #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})

(defn valid [parts] (every? #(contains? parts %) required-parts))

(def passports (map #(str/replace % "\n" " ") (str/split (slurp "day4.txt") #"\n\n")))
(def passport-parts (map parse-passport passports))

(println (count (filter valid passport-parts)))

; part 2

(defn valid-height? [height]
  (if (str/ends-with? height "cm")
    (<= 150 (Integer/parseInt (subs height 0 (- (count height) 2))) 193)
    (if (str/ends-with? height "in")
        (<= 59 (Integer/parseInt (subs height 0 (- (count height) 2))) 76)
        false)))

(defn is-int? [s] (try
                    (do (Integer/parseInt s) true)
                    (catch NumberFormatException _ false)))

(defn part-valid? [[key value]]
  (case key
    "byr" (<= 1920 (Integer/parseInt value) 2002)
    "iyr" (<= 2010 (Integer/parseInt value) 2020)
    "eyr" (<= 2020 (Integer/parseInt value) 2030)
    "hgt" (valid-height? value)
    "hcl" (re-matches #"#[0-9a-f]{6}" value)
    "ecl" (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} value)
    "pid" (and (== (count value) 9) (is-int? (str/replace value #"^0+(?!$)" "")))
    "cid" true))

(defn parts-valid? [parts]
  (every? part-valid? parts))

(println (count (filter (every-pred parts-valid? valid) passport-parts)))

(println (valid-height? "121"))