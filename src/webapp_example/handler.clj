(ns webapp-example.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [webapp-example.models.item :as item]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [clojure.data.json :refer [write-str]]
            [ring.util.response :refer [response status content-type]]))

(defn build-json-response [body resp-status]
  (-> (response body)
      (content-type "application/json")
      (status resp-status)))

(def item-routes
  (context "/items" []
    (POST "/" request (do (item/save! (:body request))
                          (build-json-response {:message "Created"} 201)))
    (GET "/" [] (build-json-response (item/all) 200))
    (GET "/:item-id" [item-id] (build-json-response (item/with-id (read-string item-id)) 200))))

(defroutes app-routes
  item-routes
  (route/not-found "Not Found"))

(defn wrap-exceptions
  [handler]
  (fn [request & opts]
    (try
      (handler request)
      (catch Exception e
        (build-json-response (write-str {:message (.getMessage e)}) 500)))))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-json-body {:keywords? true})
      wrap-json-response
      wrap-exceptions))