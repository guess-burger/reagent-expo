(ns re-frame-test.subs
  (:require [re-frame.core :as rf]))


(rf/reg-sub
  :loaded
  (fn [db _query-vector]
    (:loaded db)))

(rf/reg-sub
  :clicks
  (fn [db _query-vector]
    ;; just something simple for now
    (:clicks db)))
