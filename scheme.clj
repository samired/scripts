;; gorilla-repl.fileformat = 1

;; **
;;; # scheme.clj
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns scheme
  (:gen-class)
  (:refer-clojure :exclude [cond cons let])
  (:require [gorilla-plot.core :as plot]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
                                        ; links to blogs on scheme built on clojure once as embedded and other as external:

(def embedded "http://martintrojer.github.io/clojure/2011/11/29/scheme-as-an-embedded-dsl-in-clojure/")
(def external "http://martintrojer.github.io/clojure/2012/01/28/scheme-as-an-external-dsl-in-clojure/")

                                        ; the github repository:
(def github "https://github.com/martintrojer/scheme-clojure")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;scheme/github</span>","value":"#'scheme/github"}
;; <=

;; @@
;; define
(defmacro define [name-and-params body]
  (if (coll? name-and-params) ; function or var definition?
    `(defn ~(first name-and-params) [~@(rest name-and-params)] ~body)
    `(def ~name-and-params ~body)))

;; lambda
(defmacro lambda [& args]
  `(fn ~@args))

;; cond
(defmacro cond [& args]
  (when args
    (clojure.core/let [fst# (ffirst args)]
      (list `if (if (= fst# (symbol "else")) :else fst#)
            (second (first args))
            (clojure.core/cons 'cond (next args))))))
;; cons
(defn cons [fst snd]
  (clojure.core/cons fst (if (coll? snd) snd [snd])))

;; append
(def append concat)

;; car / cdr
(def car first)
(def cdr rest)

;; null?
(def null? empty?)

;; let
(defmacro let [& args]
  (clojure.core/let [body# (first (rest args))
                     vars# (reduce concat [] (first args))]
    `(clojure.core/let [~@vars#] ~body#)))

;; begin
(defmacro begin [& args]
  `(do ~@args))

;; display
(def display println)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;scheme/display</span>","value":"#'scheme/display"}
;; <=

;; @@
;; examples:

(define pi 3.14159)
(define radius 10)
(* pi (* radius radius))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>314.159</span>","value":"314.159"}
;; <=

;; @@
(define (factorial n)
  (define (iter product counter)
    (if (> counter n)
      product
      (iter (* counter product) (+ counter 1)))
    (iter 1 1)))
;; @@

;; @@

;; @@
