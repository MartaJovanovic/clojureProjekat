(ns guestbook.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]))
(dom/render
 [[:div {:id "hello", :class "content"} [:h1 "Hello, Auto!"]]]
 (.getElementById js/document "content"))

