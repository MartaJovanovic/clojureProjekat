-- :name save-adresa! :! :n
-- :doc creates a new adresa using the ime and adresa keys
INSERT INTO guestbook
    (ime, adresa)
VALUES
    (:ime, :adresa)
-- :name get-adresa :? :*
-- :doc selects all available adresas
SELECT *
from guestbook
