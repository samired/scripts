(ns bott
  (:require [bottle route CGIServer run template]))
  
(@route "/")
(defn index[]
  ("<h1>heloo</h1>"))
  
(run (= server localhost))