(ns webapp-example.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [webapp-example.models.item :as item]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [clojure.data.json :refer [write-str]]
            [ring.util.response :refer [response status]]))

(def item-routes
  (context "/items" []
    (POST "/" request (do (item/save! (:body request))
                          (-> (response {})
                              (status 201))))
    (GET "/" [] (response (item/all)))
    (GET "/:item-id" [item-id] (response (item/with-id item-id)))))

(defroutes app-routes
  item-routes
  (route/not-found "Not Found"))

(defn wrap-exceptions
  [handler]
  (fn [request & opts]
    (try
      (handler request)
      (catch Exception e
        (-> (response (write-str {:message (.getMessage e)})) (status 500))))))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-json-body {:keywords? true})
      wrap-json-response
      wrap-exceptions))