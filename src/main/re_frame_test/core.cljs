(ns re-frame-test.core
  (:require
    [reagent.core :as r]
    [shadow.expo :as expo]
    [re-frame-test.handlers]
    [re-frame-test.subs]
    [re-frame-test.effects]
    [re-frame-test.views :as views]
    [re-frame.core :as rf]))

(defn mount
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [views/main-panel])))

(defn init []
  (rf/dispatch-sync [:initialize])
  (mount))


(comment

  ;; run the following to get a shadow repl after connecting
  (shadow/repl :app)

  )
