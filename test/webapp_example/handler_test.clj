(ns webapp-example.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [webapp-example.handler :refer :all]))

(def items-response [{:name "get all - 1"  :quantity 10},
                     {:name "get all - 2" :quantity 12}])
(def items-by-id-response [{:name "git by id"  :quantity 10}])
(def post-item {:name "posted item" :quantity 20})

(deftest test-app
  (with-redefs [webapp-example.models.item/all (fn [] items-response)
                webapp-example.models.item/with-id (fn [_] items-by-id-response)
                webapp-example.models.item/save! (fn [_] "")]
    (testing "get /items"
      (let [response (app (mock/request :get "/items"))]
        (testing "should return status 200"
          (is (= (:status response) 200)))
        (testing "should return items"
          (is (= (:body response) (json/write-str items-response))))))
    (testing "get /items/:item-id"
      (let [response (app (mock/request :get (str "/items/5")))]
        (testing "should return status 200"
          (is (= (:status response) 200)))
        (testing "should return an item by id"
          (is (= (:body response) (json/write-str items-by-id-response))))))
    (testing "post to /items with application/json content type"
      (let [response (app (mock/content-type (mock/request :post "/items" (json/write-str post-item)) "application/json"))]
        (testing "should return status 201"
          (is (= (:status response) 201))))))

  (testing "exception handling"
    (with-redefs [webapp-example.models.item/all (fn [] (throw (Exception. "error message")))]
      (let [response (app (mock/request :get "/items") "application/json")]
        (testing "should return status 500"
          (is (= (:status response) 500))
          (is (= (:body response) (json/write-str {:message "error message"})))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
