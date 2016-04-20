(ns alfa.routes
  (:require
    [com.stuartsierra.component :as component]
    [io.pedestal.interceptor :refer [interceptor]]
    [taoensso.timbre :as log]

    [alfa.app.app :as app]
    [alfa.app.api :as api]))

(defn- make-service-interceptor
  [component-map]
  (interceptor
    {:name  ::service-interceptor
     :enter (fn [context]
              (merge context
                     component-map))}))

(defn make-routes
  [service-interceptor]
  (->> [app/make api/make]
       (mapcat #(% service-interceptor))))

(defrecord Routes [annual]
  component/Lifecycle
  (start [this]
    (log/info "Starting routes")
    (if (:routes this)
      (do
        (log/info "Routes already started")
        this)
      (do
        (log/info "Making routes")
        (assoc this :routes (-> {:annual annual}
                                make-service-interceptor
                                make-routes)))))
  (stop [this]
    (log/info "Stopping routes")
    (dissoc this :routes)))

(defn make
  []
  (log/info "Creating the Routes component")
  (component/using (map->Routes {})
                   []))
