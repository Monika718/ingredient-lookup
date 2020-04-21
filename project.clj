(defproject ingredient-lookup "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.3.443"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [com.h2database/h2 "1.4.197"]
                 [hikari-cp "1.7.5"]]
  :plugins [[lein-package "2.1.1"]
            [lein-immutant "2.0.1"]]
  :repl-options {:init-ns ingredient-lookup.core})
