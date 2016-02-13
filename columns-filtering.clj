;;; Incanter is a pretty amazing library for working with data.  With a table of data, it's easy to select rows of data to work on by filtering on the data in each row, For example:
(let [data (to-dataset [{:a 1 :b 2} {:a 3 :b 4}])]
        ($where {:a {:$gt 2}} data)) 
;;;That will select all rows where given a row, its data under column :a is greater than 2.

; if you want columns 0 and 2?
(let [data (to-dataset [{:a 1 :b 2 :c 3} {:a 3 :b 4 :c 5}])]
        ($ :all [0 2] data))

;;;if you want to select by column name, e.g. only selecting columns with name :a and :b?  This can do that:

(let [data (to-dataset [{:a 1 :b 2 :c 3} {:a 3 :b 4 :c 5}])]
        ($ :all [:a :b] data))


;;; only names containing a vowel

(let [data (to-dataset [{:a 1 :b 2 :c 3} {:a 3 :b 4 :c 5}])
        columns (filter #(re-find #"aeiou" (str %)) (:column-names data))]
        ($ columns data))