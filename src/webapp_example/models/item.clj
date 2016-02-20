(ns webapp-example.models.item
  (:require [clojure.string :as string :only [join]]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as jdbc]))

(def db-spec (str "postgresql:" (env :database-url)))

(defn all []
  (jdbc/query db-spec ["select * from items"]))

(defn with-id [id]
  (jdbc/query db-spec ["select * from items where id = ?" id]))

(defn save! [item]
  (jdbc/insert! db-spec :items item))