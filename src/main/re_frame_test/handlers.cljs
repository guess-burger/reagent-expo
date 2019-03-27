(ns re-frame-test.handlers
  (:require [re-frame.core :as rf]
            [re-frame-test.views]
            ["expo-font" :as font]))

(rf/reg-event-fx
  :initialize
  (fn [cofx _]
    {:db (assoc (:db cofx) :loaded false
                           :clicks 0)
     :load-fonts [{"Roboto_medium" (js/require "native-base/Fonts/Roboto_medium.ttf")}
                  [:fonts-loaded]
                  [:fonts-failed]
                  ]}))

(rf/reg-event-db
  :fonts-loaded
  (fn [db [_ _]]
    (.log js/console "Fonts loaded")
    (assoc db :loaded true)))

(rf/reg-event-db
  :fonts-failed
  (fn [db [_ args]]
    (.log js/console "Font didn't load" args)
    (assoc db :loaded false)))

(rf/reg-event-db
  :click
  (fn [db [_ inc-amount]]
    (update db :clicks + inc-amount)))

(rf/reg-event-db
  :reset-clicks
  (fn [db _]
    (assoc db :clicks 0)))

