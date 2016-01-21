;; gorilla-repl.fileformat = 1

;; **
;;; # Geostat
;;; 
;;; 
;; **

;; @@
(ns geostat
  (:import [java.io File])
  (:require [gorilla-plot.core :as plot]
            [clojure.data.codec.base64 :as b64]
            [gorilla-renderable.core :as render])
  (:use [clojure repl pprint]
        [clojure.string :only [join]]
        [incanter core io stats charts excel datasets]))

(defn chart->byte-array
  [chart & {:keys [plot-size aspect-ratio]
            :or {plot-size 700
                 aspect-ratio 1.618}}]
  (let [width (/ plot-size aspect-ratio)
        ba (java.io.ByteArrayOutputStream.)
        _ (org.jfree.chart.ChartUtilities/writeChartAsPNG ba chart plot-size width)]
    (.toByteArray ba)))
(defrecord ChartView [content opts])
(defn chart-view [content & opts] (ChartView. content opts))
(extend-type ChartView
  render/Renderable
  (render [self]
          (let [bytes (apply chart->byte-array (:content self) (:opts self))]
            {:type :html
             :content (format "<img src=\"data:image/PNG;base64,%1$s\"/>" (String. (b64/encode bytes)))
             :value (pr-str self)})))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ###Arithmetic Average
;;; The arithmetic average of N data is obtained by adding the quantities and dividing by
;;; the number of data in the sample. This is commonly expressed mathematically as: $$ k _{ar}  = \frac {1} {N} \sum ^{N} _i k_i $$
;; **

;; @@

;; @@

;; @@

;; @@
