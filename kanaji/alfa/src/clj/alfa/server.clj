(ns alfa.server
  (:require
    [com.stuartsierra.component :as component]
    [io.pedestal.http :as pedestal]
    [taoensso.timbre :as log]))

(defrecord Server [service server]
  component/Lifecycle
  (start [this]
    (if server
      (do
        (log/info "Server already started")
        this)
      (let [runnable-service (pedestal/create-server (get service :service-map))]
        (pedestal/start runnable-service)
        (log/info "Server started")
        (assoc this :server runnable-service))))
  (stop [this]
    (if (not server)
      (do (println "Server already stopped")
          this)
      (do
        (log/info "Stopping server")
        (pedestal/stop server)
        (dissoc this :server)))))

(defn make
  []
  (component/using (map->Server {})
                   [:service]))
