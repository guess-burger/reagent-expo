(ns re-frame-test.views
  (:require [steroid.rn.core :as rn]
            [steroid.views :as views]
            [re-frame.core :as re-frame]
            [steroid.fx :as fx]
            [steroid.subs :as subs]
            [shadow.expo :as expo]
            [steroid.rn.navigation.core :as rnn]
            [steroid.rn.navigation.stack :as stack]
            steroid.rn.navigation.events
            [reagent.core :as r]))

(fx/defn
  init-app-db
  {:events [:init-app-db]}
  [_]
  {:db {:counter 0}})

(fx/defn
  update-counter
  {:events [:update-counter]}
  [{:keys [db]}]
  {:db (update db :counter inc)})

(re-frame/reg-sub
  :counter-with-delta
  :<- [:counter]
  :<- [:delta]
  (fn [[counter delta]]
    (+ counter delta)))


(subs/reg-root-subs #{:counter :delta})


(views/defview home-screen []
               (views/letsubs [counter [:counter-with-delta]]
                              [rn/safe-area-view {:style {:flex 1}}
                               [rn/view {:style {:align-items :center :justify-content :center :flex 1}}
                                [rn/text (str "Counter with delta: " counter)]
                                [rn/touchable-opacity {:on-press #(re-frame/dispatch [:update-counter])}
                                 [rn/view {:style {:background-color :gray :padding 5}}
                                  [rn/text "Update counter"]]]
                                [rn/touchable-opacity {:on-press #(re-frame/dispatch [:navigate-to :modal])
                                                       :style {:margin-top 20}}
                                 [rn/view {:style {:background-color :gray :padding 5}}
                                  [rn/text "Open modal"]]]]]))

(defn modal-screen []
  [rn/view {:style {:align-items :center :justify-content :center :flex 1}}
   [rn/touchable-opacity {:on-press #(re-frame/dispatch [:navigate-back])}
    [rn/view {:style {:background-color :gray :padding 5}}
     [rn/text "Navigate back"]]]])

(views/defview root-stack []
               (views/letsubs [[navigator screen] (stack/create-stack-navigator)
                               home-comp (rn/reload-comp home-screen)
                               modal-comp (rn/reload-comp modal-screen)]
                              {:component-did-mount (rnn/create-mount-handler #(re-frame/dispatch [:init-app-db]))}
                              [rnn/navigation-container {:ref rnn/nav-ref-handler}
                               [navigator {:mode :modal}
                                [screen {:name      :home
                                         :component home-comp}]
                                [screen {:name      :modal
                                         :component modal-comp}]]]))

(defn init []
  (re-frame/dispatch [:init-app-db])
  (expo/render-root (r/as-element [root-stack]))
  #_(rn/register-reload-comp "ClojureRNProject" root-comp))