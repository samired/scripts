;; non-lazy version
(defn insert
  "insert key into the lst which is assumed to be already sorted"
  [key lst]
  (if (empty? lst)
    (seq [key])
    (let [[x & xs] lst]
      (if (< key x)
(cons key lst)
(cons x (insert key xs))))))

(defn in-sort
  "insertion sort of the collection"
  [coll]
  (if (empty? coll)
    nil
    (insert (first coll) (in-sort (rest coll)))))