(ns ingredient-lookup.cache.database)

(defprotocol ICachedMap
  (add-product! [this name ingredient])
  (get-product [this name])
  (get-all [this])
  (delete-product! [this name])
  (stop [this]))