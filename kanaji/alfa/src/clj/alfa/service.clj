(ns alfa.service
  (:require
    [com.stuartsierra.component :as component]
    [io.pedestal.http :as pedestal]
    [taoensso.timbre :as log]))

(defrecord Service [service-map routes]
  component/Lifecycle
  (start [this]
    (log/info "Starting the pedestal service")
    (if (get-in this [:service-map ::pedestal/routes])
      (do
        (log/info "Service map already there"))
      (do
        (log/info "Making service-map")
        (assoc-in this
                  [:service-map ::pedestal/routes]
                  (:routes routes)))))
  (stop [this]
    (log/info "Stopping the pedestal service")
    (assoc-in this [:service-map ::pedestal/routes] nil)))

(defn make
  "Constructor function for Service component, it doesnt't take any input
  but depends on Routes component."
  ([] (make {:env                     :dev
             ::pedestal/join?         false
             ::pedestal/routes        nil
             ::pedestal/resource-path "/public"
             ::pedestal/type          :jetty
             ::pedestal/port          3000}))
  ([service-map]
   (log/info "Creating pedestal Service component")
   (component/using
     (map->Service {:service-map service-map})
     [:routes])))
