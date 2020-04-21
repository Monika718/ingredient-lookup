(ns ingredient-lookup.cache.jdbc
  (:require [ingredient_lookup.cache.database :refer [ICachedMap]]
            [clojure.java.jdbc :as jdbc]
            [hikari-cp.core :as hikari]
            [taoensso.timbre :as log]
            [clojure.walk :as walk]
            [clojure.string :as str]))

(defprotocol Startable
  (start [component]))

(defrecord JDBCCachedMap [db]
  ICachedMap
  Startable
  (start [this]
    (jdbc/db-do-commands db [(jdbc/create-table-ddl :version_log
                                                    [[:id "IDENTITY" :primary :key]
                                                     [:db_version "INT"]
                                                     [:update_time "TIMESTAMP WITH TIME ZONE"]]
                                                    {:conditional? true})])
    this))

(def h2-settings
  {:adapter   "h2"
   :url   "jdbc:h2:~/ingredient-lookup/h2cache"})

(defn make-cache
  []
  (start (->JDBCCachedMap {:datasource (hikari/make-datasource h2-settings)})))