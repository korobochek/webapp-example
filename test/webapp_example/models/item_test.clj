(ns webapp-example.models.item-test
  (:require [clojure.test :refer :all]
            [webapp-example.models.item :as subject]
            [clojure.java.jdbc :as jdbc]))

(defn clear-db [test-fn]
  (jdbc/delete! subject/db-spec :items [])
  (test-fn))

(use-fixtures :each clear-db)

(deftest all
  (jdbc/insert! subject/db-spec :items {:name "test" :quantity 1})
  (testing "returns all available records"
    (is (= 1 (count (subject/all)))))
  (testing "returns record with correct name"
    (is (= "test" (:name (first (subject/all))))))
  (testing "returns record with correct quantity"
    (is (= 1 (:quantity (first (subject/all)))))))

(deftest save
  (subject/save! {:name "blah" :quantity 10})
  (let [result (jdbc/query subject/db-spec ["select * from items"])]
    (testing "saves record to items table"
      (is (= 1 (count result))))
    (testing "records correct name"
      (is (= "blah" (:name (first result)))))
    (testing "records correct quantity"
      (is (= 10 (:quantity (first result)))))))

(deftest failing-save
  (testing "thows error is trying to save incorrect values"
    (is (thrown? org.postgresql.util.PSQLException (subject/save! {:rubbish "boom"})))))
