;; gorilla-repl.fileformat = 1

;; **
;;; # د
;;; ## لغة البرمجة العربية المبنية علي سكيم و كلوجر
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns dal
  (:require [gorilla-plot.core :as plot]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defmacro د [& args]
`(fn ~@args))

(defmacro عرف [& args]
`(def ~@args))

(def اطبع print)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;dal/اطبع</span>","value":"#'dal/اطبع"}
;; <=

;; @@
(عرف جمع
     (د [س ش]
        (+ س ش)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;dal/جمع</span>","value":"#'dal/جمع"}
;; <=

;; @@
(جمع 523 34)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>557</span>","value":"557"}
;; <=

;; @@

;; @@
