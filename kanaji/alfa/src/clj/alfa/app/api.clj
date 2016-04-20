(ns alfa.app.api
  (:require
    [cheshire.core :as json]
    [clojure.java.io :as io]
    [clojure.string :as cs]
    [io.pedestal.http.route.definition :refer [expand-routes]]
    [io.pedestal.interceptor :refer [interceptor]]
    [net.cgrand.enlive-html :as html]
    [ring.util.response :as resp]))

(def json-maker
  (interceptor
    {:name  ::json-maker
     :leave (fn [{:keys [json] :as context}]
              (assoc context
                :response (-> (resp/response (json/generate-string (-> {}
                                                                       (assoc :JSON json)) {:pretty true}))
                              (resp/content-type "application/json;charset=UTF-8"))))}))

(def api-home
  (interceptor
    {:name  ::api-home
     :leave (fn [context]
              (println "API HOME")
              (assoc-in context [:json]
                        {:text "API HOME"}))}))

(defn make
  "Create the route for the applications"
  [service-interceptor]
  (expand-routes
    [[["/api" ^:interceptors [service-interceptor
                              json-maker]
       {:get api-home}]]]))
