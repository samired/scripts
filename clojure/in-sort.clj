(defn insertion-sort
  [lst]
  (if (empty? lst)
    nil
    (insert (first lst)
            (insertion-sort (rest lst)))))

(defn insert
  [key list]
  (if (empty? list)
    (seq [key])
    (let [[head & tail] list] ;;split to head and tail
      (if (< key head)
        (cons key list)                   ;;put as head
        (cons head (insert key tail)))))) ;; recursively put in the tail
