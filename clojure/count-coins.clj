;; This buffer is for notes you don't want to save, and for Lisp evaluation.
;; If you want to create a file, visit that file with C-x C-f,
;; then enter the text in that file's own buffer.

(defn count-coins [change]
  (if change
    (apply + (vals change))
    Double/POSITIVE_INFINITY))

(defn update [m k f default]
  (assoc m k (f (get m k default))))

(defn change-increment [change coin]
  (when change
    (update change coin inc 0)))

;; This is now the private helper function, don't use this directly
(defn ^:dynamic make-change [denominations amount]
  (cond
   (neg? amount) nil
   (zero? amount) {}
   (empty? denominations) nil
   :else
   (let [option1 (change-increment
                  (make-change denominations (- amount (first denominations)))
                  (first denominations)),
         option2 (make-change (rest denominations) amount)]
     (min-key count-coins option1 option2))))

;; This is the new public function we use to make change. 
(defn make-change-fast [denominations amount]
  (binding [make-change (memoize make-change)]
    (last
     (for [i (range (inc amount))]
       (make-change denominations i)))))