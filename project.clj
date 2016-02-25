(defproject webapp-example "0.1.0-SNAPSHOT"
  :description "simple webapp"
  :url "http://simple-webapp.com/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.0.2"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.clojure/data.json "0.2.6"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]]
  :plugins [[lein-ring "0.9.7"]
            [clj-sql-up "0.3.7"]
            [lein-environ "1.0.2"]]
  :ring {:handler webapp-example.handler/app}
  :profiles
    {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                          [ring/ring-mock "0.3.0"]]
           :env {:database-url "//webapp-example@localhost:5432/webapp-example"}}
     :test {:dependencies [[org.clojure/tools.nrepl "0.2.11"]]
            :env {:database-url "//webapp-example@localhost:5432/webapp-example-test"}}}
  :clj-sql-up {:database "jdbc:postgresql://webapp-example@localhost:5432/webapp-example"
               :database-test "jdbc:postgresql://webapp-example@localhost:5432/webapp-example-test"
               :deps [[org.postgresql/postgresql "9.3-1100-jdbc4"]]}
  :aliases {"db-migrate"       ["clj-sql-up" "migrate"]
            "db-rollback"      ["clj-sql-up" "rollback"]})
