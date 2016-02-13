(ns hydeval.gui
  (:gen-class)
  (:import [javax.swing JFrame JPanel])
  (:import [org.jfree.chart ChartPanel])
  (:import [java.awt  Toolkit Container Dimension BorderLayout])
  (:use [incanter core stats charts]))


;(set-theme :dark)
(defn f [x y] (sin (sqrt (plus (sq x) (sq y)))))


(def dyn 
(let [x (range -3 3 0.1)]
  (dynamic-xy-plot [mean (range -3 3 0.1)
                    sd (range 0.1 10 0.1)]
                   [x (pdf-normal x :mean mean :sd sd)]
                   :title "Normal PDF Plot")))

(let
    [^JFrame frame (JFrame. "plot")
     ^JPanel panel1 (JPanel. (BorderLayout. 10 10))
     ^JPanel panel2 (JPanel. (BorderLayout. 10 10))]
  
  (doto panel1
    (.add (^Component ChartPanel. (function-plot sin -10 10)) BorderLayout/EAST)
    (.add (^Component ChartPanel. (function-plot cos -13 12)) BorderLayout/WEST))

  (doto panel2
    (.add (^Component ChartPanel. (heat-map f -10 10 -15 15)) BorderLayout/EAST)
    (.add (^Component ChartPanel. (heat-map f -10 10 -10 10 :color? false)) BorderLayout/WEST))
 
  (doto frame 
    (.setExtendedState (JFrame/MAXIMIZED_BOTH))
    (.add panel1 BorderLayout/NORTH)
    (.add panel2 BorderLayout/SOUTH)
    (.setVisible true)))

(defn -main [& args])


