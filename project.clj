(defproject webapp-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [org.clojure/java.jdbc "0.4.2"]]
  :plugins [[lein-ring "0.9.7"]
            [clj-sql-up "0.3.7"]]
  :ring {:handler webapp-example.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}}
  :clj-sql-up {:database "jdbc:postgresql://webapp-example@localhost:5432/webapp-example"
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]})