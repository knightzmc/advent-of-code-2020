(use '[clojure.string :as str])

(let [file (slurp "day1.txt")
    lines (set (map #(Integer/parseInt %) (str/split-lines file)))]
        (first (filter (fn [l] ()))))