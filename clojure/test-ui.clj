(ns ui.test
  (:import [javax.swing JFrame JPanel JButton JTextField JLabel JTextArea JTextPane]))


(defmacro on-action [component event & body]
  `(. ~component addActionListener
      (proxy [java.awt.event.ActionListener] []
        (actionPerformed [~event] ~@body))))

(defn counter-app []
  (let [counter (atom 0)
        label (JLabel. "Counter: 0")
        button (doto (JButton. "Add 1")
                 (on-action evnt  ;; evnt is not used
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

(defn frm[]
  (let [counter (atom 0)
        label (JLabel. "Counter: 0")
        area   (doto (JTextArea. 20 20))
        button (doto (JButton. "Add 1") (on-action evnt (.setText area (str "Counter: " (swap! counter inc)))))
        panel  (doto (JPanel.)
                 (.setOpaque true)
                 (.add area)
                
                 (.add label)
                 (.add button))]
    (doto (JFrame. "Counter App")
      (.setContentPane panel)
          (.setSize 310 510)
          (.setVisible true))))