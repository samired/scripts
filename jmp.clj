(ns plot.core
  (:gen-class)
  (:import [org.math.plot Plot2DPanel])
  (:import [javax.swing JFrame]))

(set! *warn-on-reflection* true)

(def plot (Plot2DPanel.))
(def frame (JFrame. "plot")) 

  (doto plot
    (.addLegend "SOUTH")
    (.addLinePlot "samir" (double-array [1 2 3]) (double-array [22 33 44])))

  (doto frame 
    (.setContentPane plot)
    (.setSize 500 500)
    (.setVisible true))


