(ns examples.pyside.tutorial.t1
    (:require [PySide.QtGui :as gui])
	(:require [time :as t]))




(let [app (gui/QApplication  sys/argv)
      hello (gui/QPushButton (t/ctime))]
      (doto hello 
	    (.resize 200 30)
        (.show))
      (sys/exit (.exec_ app)))
      