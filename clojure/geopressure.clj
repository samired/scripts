;; gorilla-repl.fileformat = 1

;; **
;;; # Geopressure
;;; 
;;; Showing DX, DXc, C1/C2
;; **

;; @@
(ns geopressure
  (:require [gorilla-plot.core :as plot])
  (:use [incanter core io charts excel datasets stats])
  (:use [clojure repl pprint]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### Defining formulas
;;; first we define formulas to be used on data
;; **

;; @@
(defn d [R N D W]
  "d = log10(R/60N)/log10(12W/106D) where : R=ROP (ft/hr) N=RPM (rev/min) W=WOB (lbs) D=bit size (ins)"
  (/ (Math/log10 (/ R (* 60 N)))
     (Math/log10 (/ (* 12 W) (* 1000 D)))))


(defn dxc [MW1 MW2 R N D W]
  (* (d R N D W) (/ MW1 MW2)))


(defn c1c2 [C1 C2]
  "C1 devided by C2 for fluid type indication"
  (if (zero? C2) 0 (/ C1 C2)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;geopressure/c1c2</span>","value":"#'geopressure/c1c2"}
;; <=

;; @@
(def drlg-data (read-dataset "C:\\Sync\\Drafts\\geopressure\\test.las" :header true))
(def gas-data (read-dataset "C:\\Sync\\Drafts\\geopressure\\gas.las" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;geopressure/gas-data</span>","value":"#'geopressure/gas-data"}
;; <=

;; @@
 (def DEPTH ($ :DEPTH drlg-data))
  (def ROP ($ :ROP drlg-data))
  (def RPM ($ :RPM drlg-data))
  (def WOB ($ :WOB drlg-data))
  (def BIT ($ :BITSIZE drlg-data))

  (def Dx (map #(d %1 %2 %3 %4) ROP RPM BIT WOB))

  (def C1 ($ :MUDGAS_C1 gas-data))
  (def C2 ($ :MUDGAS_C2 gas-data))
  (def C1C2 (map #(c1c2 %1 %2) C1 C2))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;geopressure/C1C2</span>","value":"#'geopressure/C1C2"}
;; <=

;; @@
(def plot1 (xy-plot DEPTH Dx))
(def plot2 (area-chart DEPTH Dx))
(def plot3 (xy-plot DEPTH C1C2))


;(def lm1 (linear-model DEPTH Dx))
;(add-lines plot1 DEPTH (:fitted lm1))

;(def lm2 (linear-model DEPTH Dx :intercept false))
;(add-lines plot1 DEPTH (:fitted lm2))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;geopressure/plot3</span>","value":"#'geopressure/plot3"}
;; <=

;; @@
(view plot1)
(view plot2)
(view plot3)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;ChartFrame org.jfree.chart.ChartFrame[frame24,0,0,500x400,layout=java.awt.BorderLayout,title=Incanter Plot,resizable,normal,defaultCloseOperation=DISPOSE_ON_CLOSE,rootPane=javax.swing.JRootPane[,8,30,484x362,layout=javax.swing.JRootPane$RootLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777673,maximumSize=,minimumSize=,preferredSize=],rootPaneCheckingEnabled=true]&gt;</span>","value":"#<ChartFrame org.jfree.chart.ChartFrame[frame24,0,0,500x400,layout=java.awt.BorderLayout,title=Incanter Plot,resizable,normal,defaultCloseOperation=DISPOSE_ON_CLOSE,rootPane=javax.swing.JRootPane[,8,30,484x362,layout=javax.swing.JRootPane$RootLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777673,maximumSize=,minimumSize=,preferredSize=],rootPaneCheckingEnabled=true]>"}
;; <=

;; @@

;; @@
