(import '(javax.swing JLabel JButton JPanel JFrame))

(defmacro on-action [component event & body]
  `(. ~component addActionListener
      (proxy [java.awt.event.ActionListener] []
        (actionPerformed [~event] ~@body))))
		
(defn counter-app []
  (let [counter (atom 0)
        label (JLabel. "Counter: 0")
        button (doto (JButton. "Add 1")
                 (on-action event  ;; evnt is not used
                   (.setText label
                      (str "Counter: " (swap! counter inc)))))
        panel (doto (JPanel.)
                (.setOpaque true)
                (.add label)
                (.add button))]
    (doto (JFrame. "Counter App")
      (.setContentPane panel)
      (.setSize 300 100)
      (.setVisible true))))
	  
(counter-app)
	  
