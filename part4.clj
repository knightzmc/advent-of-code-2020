(use '[clojure.string])

(defn get-part [s] (.substring s 0 (index-of s ":")))
(defn get-value [s] (.substring s (index-of s ":")))
(defn get-part-value [s] [(get-part s) (get-value s)])

(defn get-parts [s] (map get-part (split s #"\s")))


(defn match [value conditions]
    (last (last (filter (fn [condition] (== (nth condition 0) value)) (partition 2 conditions)))))

(match 3
    [2 "a"
     3 "b"])

(def required-parts #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"})
(defn valid [parts] (every? (set parts) required-parts))

(def passports (map #(replace % "\n" " ") (split (slurp "day4.txt") #"\n\n")))
(def passport-parts (map get-parts passports))

(println (count (filter valid passport-parts)))

; part 2

(defn parts-valid [parts]
    (every? (fn [part] 
        (match)) parts))

