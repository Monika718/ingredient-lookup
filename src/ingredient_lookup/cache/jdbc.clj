(ns ingredient-lookup.cache.jdbc
  (:require [ingredient-lookup.cache.database :refer [ICachedMap]]
            [clojure.java.jdbc :as jdbc]
            [hikari-cp.core :as hikari]
            [clojure.walk :as walk]
            [clojure.string :as str]))

(defprotocol Startable
  (start [component]))

(defrecord JDBCCachedMap [db]
  ICachedMap
  (add-product! [this name ingredient]
    (when (empty? (jdbc/query db ["select name from product where name = ?" name]))
      (jdbc/insert! db "product" {:name name :ingredient ingredient})))

  (get-product [this name]
    (jdbc/query db ["select name, ingredient from product where name = ?" name]))

  (get-all [this]
    (jdbc/query db ["select name, ingredient from product"]))

  (delete-product! [this name]
    (jdbc/delete! db :product ["name ?" name]))

  (stop [this]
    (hikari/close-datasource (:datasource db)))

  Startable
  (start [this]
    (jdbc/db-do-commands db [(jdbc/create-table-ddl :product
                                                    [[:name "VARCHAR" :primary :key]
                                                     [:ingredient "VARCHAR"]]
                                                    {:conditional? true})])
    this))

(def h2-settings
  {:adapter   "h2"
   :url   "jdbc:h2:/ingredient-lookup/h2cache"})

(defn make-cache
  []
  (start (->JDBCCachedMap {:datasource (hikari/make-datasource h2-settings)})))