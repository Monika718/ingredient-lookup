(ns ingredient-lookup.process-item
  (:require [clojure.string :as str]))

(defn trim-str
  "Keep letters, digits, slash and comma of a given string"
  [s]
  (let [no-space-s (str/replace s #", | ," ",")
        cleaned-up-s (apply str (re-seq #"[a-zA-Z0-9\-\s,\/]" no-space-s))]
    cleaned-up-s))

(defn blacklist-lookup
  "Take a string and
   Check if any of the items in the string is in the blacklist"
  [st bl]
  (let [items (str/split (trim-str st) #",|\/")
        lower-case-items (map str/lower-case items)
        trimmed-item (map str/trim lower-case-items)]
    (if (bl trimmed-item)
      (println "Found " (bl trimmed-item) " in the ingredient. This product is not suitable for pregnant women.")
      (println "Product is safe for pregnant women to use."))))