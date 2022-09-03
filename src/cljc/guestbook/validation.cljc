(ns guestbook.validation
  (:require
   [struct.core :as st]))

(def adresa-schema
  [[:ime
    st/required
    st/string]
   [:adresa
    st/required
    st/string
    {:message "Adresa mora biti duza od 7"
     :validate (fn [msg] (>= (count msg) 7))}]])

(defn validate-adresa [params]
  (first (st/validate params adresa-schema)))

