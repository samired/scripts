(ns sudoku
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(defn get-square [grid x y]
  (for [x (range x (+ x 3))
        y (range y (+ y 3))]
    (get-in grid [x y])))

(defn init [grid [pos value]]
  (== (get-in grid pos) value))

(defn sudokufd [hints]
  (let [vs   (repeatedly 81 lvar) 
        grid (->> vs (partition 9) (map vec) (into []))
        rows grid
        cols (apply map vector grid)
        sqs  (for [x (range 0 9 3)
                   y (range 0 9 3)]
               (get-square grid x y))]
    (run-nc 1 [q]
      (== q grid)
      (everyo #(infd % (domain 1 2 3 4 5 6 7 8 9)) vs)
      (everyo (partial init grid) hints)
      (everyo distinctfd rows)
      (everyo distinctfd cols)
      (everyo distinctfd sqs))))