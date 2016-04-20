(defproject
  alfa "0.1.0-SNAPSHOT"
  :description "Clojure template. Pedestal + system component + re-frame"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure-csv/clojure-csv "2.0.2"]
                 [com.stuartsierra/component "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]

                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]
                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.14"]
                 [org.slf4j/jcl-over-slf4j "1.7.14"]

                 [hiccup "1.0.5"]
                 [enlive "1.1.6"]
                 [cheshire "5.5.0"]
                 [com.taoensso/timbre "4.2.1"]
                 [io.netty/netty "4.0.0.Alpha8"]

                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1"]
                 [re-frame "0.6.0"]
                 [secretary "1.2.3"]]

  :min-lein-version "2.5.3"
  :resource-paths ["resources" "config"]
  :source-paths ["src/clj"]
  :profiles {:dev {:source-paths ["dev"]}}

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "alfa.core/mount-root"}
                        :compiler {:main alfa.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main alfa.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]})
