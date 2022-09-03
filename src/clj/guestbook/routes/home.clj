(ns guestbook.routes.home
  (:require
   [guestbook.layout :as layout]
   [guestbook.db.core :as db]
   [clojure.java.io :as io]
   [guestbook.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
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

(defn save-adresa! [{:keys [params]}]
  (if-let [errors (validate-adresa params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-adresa! params)
      (response/found "/"))))

(defn home-page [{:keys [flash] :as request}]
  (layout/render
   request
   "home.html"
   (merge {:adrese (db/get-adresa)}
          (select-keys flash [:ime :adresa :errors]))))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/adresa" {:post save-adresa!}]
   ["/about" {:get about-page}]])

