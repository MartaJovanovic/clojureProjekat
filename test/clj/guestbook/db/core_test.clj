(ns guestbook.db.core-test
  (:require
   [guestbook.db.core :refer [*db*] :as db]
   [java-time.pre-java8]
   [luminus-migrations.core :as migrations]
   [clojure.test :refer :all]
   [next.jdbc :as jdbc]
   [guestbook.config :refer [env]] [mount.core :as mount]))
(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'guestbook.config/env
     #'guestbook.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))
(deftest test-adrese
  (jdbc/with-transaction [t-conn *db* {:rollback-only true}]
    (is (= 1 (db/save-adresa!
              t-conn
              {:ime "Marko Aleksic"
               :adresa "Dalmatinska 25"}
              {:connection t-conn})))
    (is (= {:ime "Marko Aleksic"
            :adresa "Dalmatinska 25"}
           (-> (db/get-adresa t-conn {})
               (first)
               (select-keys [:ime :adresa]))))))
