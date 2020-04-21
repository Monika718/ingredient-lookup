(ns ingredient-lookup.process-item
  (:require [clojure.string :as str]))

(defn trim-str
  "Remove keep only letters/digits of a given string"
  [s]
  (let [no-space-s (str/replace s #", | ," ",")
        cleaned-up-s (apply str (re-seq #"[a-zA-Z0-9\-\s,\/]" no-space-s))]
    cleaned-up-s))

(defn look-up-blacklist
  "Take a string of a list of items
   Check if any of the items in the list is in the set"
  [st set-table]
  (let [items (str/split (trim-str st) #",|\/")
        lower-case-items (map str/lower-case items)
        trimmed-item (map str/trim lower-case-items)]
    (if (set-table trimmed-item)
      (println "Found " (set-table trimmed-item) " in the ingredient. This product is not suitable for pregnant women.")
      (println "Product is safe for pregnant women to use."))))