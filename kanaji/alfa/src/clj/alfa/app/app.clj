(ns alfa.app.app
  (:require
    [io.pedestal.http :as http]
    [io.pedestal.http.route.definition :refer [expand-routes]]
    [io.pedestal.interceptor :refer [interceptor]]
    [ring.util.response :as ring]
    [hiccup.core :as hiccup]))

(defn home-block
  []
  (hiccup/html
    [:html {:lang "en"}
     [:head
      [:meta {:charset "utf-8"}]
      [:title "Made with lein new pancang"]
      ;;[:link {:href "css/bootstrap.min.css", :rel "stylesheet"}]
      ;;[:script {:src "js/jquery.min.js"}]
      ;;[:script {:src "js/bootstrap.min.js"}]
      [:link {:href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" :rel "stylesheet"}]
      [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"}]
      [:script {:src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"}]
      [:script {:src "js/compiled/app.js"}]
      [:link {:href "style.css" :rel "stylesheet"}]]
     [:body
      [:h1 "Hello-world! From pancang with lope"]
      [:div {:id "app"}]
      [:script "alfa.core.init();"]]]))

(def home-page
  (interceptor
    {:name  ::home-page
     :leave (fn [context]
              (assoc context :response (ring/response (home-block))))}))

(defn make
  "Create the route for the applications"
  [service-interceptor]
  (expand-routes
    [[["/" ^:interceptors [service-interceptor
                           http/html-body]
       {:get home-page}]]]))
