(ns re-frame-test.views
  (:require ["react-native" :as rn]
            ["react" :as react]
            [re-frame.core :as rf]
            ["native-base" :as nb]
            ["expo" :as expo]))

(defn basic-view []
  [:> nb/Container {}
   [:> nb/Header
    [:> nb/Left
     [:> nb/Button {:transparent true
                    :on-press #(rf/dispatch [:reset-clicks])}
      [:> nb/Icon {:ios     "ios-trash"
                   :android "md-trash"
                   :style   {:color "white"}}]]]
    [:> nb/Body
     [:> nb/Title "Clicker!"]]]
   [:> nb/Content
    [:> nb/Text (str "You've clicked " @(rf/subscribe [:clicks]) " times")]
    [:> nb/Button {:block true
                   :success true
                   :on-press #(rf/dispatch [:click 1])}
       [:> nb/Text "Click 1"]]
    [:> nb/Button {:block true
                   :warning true
                   :on-press #(rf/dispatch [:click 2])}
     [:> nb/Text "Click 2"]]
    [:> nb/Button {:block true
                   :danger true
                   :on-press #(rf/dispatch [:click 3])}
     [:> nb/Text "Click 3"]]]])


(defn main-panel []
  (fn []
    (let [loaded? @(rf/subscribe [:loaded])]
      (if loaded?
        (basic-view)
        [:> expo/AppLoading]))))