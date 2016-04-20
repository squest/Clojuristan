(ns user)

(defn dev
  []
  (require 'dev)
  (in-ns 'dev))

(defn go
  []
  (println "Don't you mean (dev)"))
