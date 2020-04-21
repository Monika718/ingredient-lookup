(ns ingredient-lookup.utils
  (:require [ingredient-lookup.cache.jdbc :as jdbc-db]
            [ingredient-lookup.cache.database :as db]))

(def cache (atom nil))

(defn get-cache []
  (jdbc-db/make-cache))

(defn start-cache []
  (reset! cache (get-cache)))

(defn stop-cache []
  (db/stop @cache)
  (reset! cache nil))

(defn get-all-products
  []
  (db/get-all @cache))

(defn add-product!
  [name ingredient]
  (db/add-product! @cache name ingredient))

(defn get-product
  [name]
  (db/get-product @cache name))

(defn get-item-ingredient
  [name]
  (-> name get-product first :ingredient))

(defn delete-product!
  [name]
  (db/delete-product! @cache name))