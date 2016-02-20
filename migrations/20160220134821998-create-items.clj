;; migrations/20160220134821998-create-items.clj

(defn up []
  ["CREATE TABLE items (id SERIAL PRIMARY KEY,
                        name varchar(50) NOT NULL,
                        quantity INTEGER NOT NULL,
                        created_at timestamp NOT NULL default CURRENT_TIMESTAMP)"])

(defn down []
  ["DROP TABLE items"])
