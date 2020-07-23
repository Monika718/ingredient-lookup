(ns ingredient-lookup.process-item
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn trim-str
  "Keep letters, digits, slash and comma of a given string"
  [s]
  (let [no-space-s (str/replace s #", | ,|･" ",")
        cleaned-up-s (apply str (re-seq #"[a-zA-Z\-\s,\/]" no-space-s))]
    cleaned-up-s))

(defn blacklist-lookup
  "Take a string and
   Check if any of the items in the string is in the blacklist"
  [st bl]
  (let [items (str/split (trim-str st) #",|\/")
        lower-case-items (map str/lower-case items)
        trimmed-item (map str/trim lower-case-items)]
    (doseq [s trimmed-item]
      (doseq [b ingredient-lookup.blacklist/makeup-blacklist]
        (if (str/includes? s b)
          (println "Found" b "in the ingredient"))))
    #_(if-not (empty? (set/intersection bl (set trimmed-item)))
      (println "Found" (set/intersection bl (set trimmed-item)) "in the ingredient. This product is not suitable for pregnant women.")
      (println "Product is safe for pregnant women to use."))))