(ns re-frame-test.effects
  (:require [re-frame.core :as rf]
            ["expo-font" :as font]))

(rf/reg-fx
  :load-fonts
  (fn [[font-map on-success on-fail]]
    (let [prom (-> font-map
                   clj->js
                   font/loadAsync)
          on-fulfilled (fn [value]

                         (rf/dispatch (conj on-success value)))
          on-rejected (fn [reason]
                        (rf/dispatch (conj on-fail reason)))]
      (.then prom
             on-fulfilled
             on-rejected))))


