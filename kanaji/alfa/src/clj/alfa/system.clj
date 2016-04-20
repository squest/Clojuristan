(ns alfa.system
  (:require
    [clojure.edn :as edn]
    [clojure.java.io :as io]
    [clojure.tools.namespace.repl :as ns-repl]
    [com.stuartsierra.component :as component]
    [taoensso.timbre :as log]

    [alfa.routes :as routes]
    [alfa.server :as server]
    [alfa.service :as service]))

(defn config []
  (-> "config.edn"
      io/resource
      slurp
      edn/read-string))

(defn create-system
  "It creates a system, and return the system, but not started yet"
  [conf mode]
  (let []
    (component/system-map
      :routes (routes/make)
      :service (service/make)
      :server (server/make))))

(defonce system (create-system (config) :dev))

(defn system-map
  "A Function to print-out the system map to the repl.
  Basically a development tool only"
  ([] (system-map []))
  ([path-to-key]
   (clojure.pprint/pprint (get-in system path-to-key))))

(defn init
  "Function to initiate the system"
  ([] (init :dev))
  ([mode]
   (alter-var-root
     #'system
     (constantly (create-system (config) mode)))))

(defn start
  "Function to start the system, that is starting all the components and resolving
  the dependencies of each component."
  []
  (alter-var-root
    #'system
    component/start))

(defn stop
  "Function to stop the system, and stop all of its components according to the
  dependencies of each component."
  []
  (alter-var-root
    #'system
    (fn [s] (component/stop s))))

(defn go
  "The function to be called from the REPL for starting the system"
  []
  (init)
  (start)
  (log/info "System is fully operational, here's some important info")
  system)

(defn reset
  "Reset the system in REPL after changing some codes"
  []
  (stop)
  (ns-repl/refresh :after 'alfa.system/go))

(defn reset-all
  "Reset the system in REPL after changing some codes"
  []
  (stop)
  (ns-repl/refresh-all :after 'alfa.system/go))

