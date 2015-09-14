(ns collision.core)

(defn add [& v]
  (let [x (map first v)
	y (map second v)] 
    [(apply + x) (apply + y)]))

(defn subtract [u v]
  (let [[vx vy] v] 
    (add u [(- vx) (- vy)])))

(defn multiply [v s]
  [(* (first v) s) (* (second v) s)])

(defn magnitude [v]
  (let [[x y] v] 
    (Math/sqrt (+ (* x x) (* y y)))))

(defn normalize [v]
  (let [[x y] v
	mag (magnitude v)] 
    (if-not (= mag 0)
      [(/ x mag) (/ y mag)]
      [0 0])))

(defn dot-product [u v]
  (let [[ux uy] u [vx vy] v] 
    (+ (* ux vx) (* uy vy))))

(defn project [u v]
  (let [unit-v (normalize v)
	dot (dot-product u unit-v)]
    (multiply unit-v dot)))

(defn closest-point-on-line [a b c]
  (let [ac (subtract c a)
	ab (subtract b a)
	proj-mag (dot-product ac (normalize ab))]
    (cond (< proj-mag 0) a
	  (> proj-mag (magnitude ab)) b
	  :default (add (project ac ab) a))))

(defn path-clean [a b c r]
  (let [closest (closest-point-on-line a b c)
	distance (magnitude (subtract c closest))]
    (if (<= distance r)
      false true)))

;;
;;
;;

(defn draw-object [g [x y] rad color]
  (let [offset (int (/ rad 2))
	x (- x offset)
	y (- y offset)]
    (doto g
      (.setColor color)
      (.fill (java.awt.geom.Ellipse2D$Double. x y rad rad)))))

(defn draw-bounds [g [x y rad] color]
  (let [x (- x rad)
	y (- y rad)]
    (doto g
      (.setColor color)
      (.draw (java.awt.geom.Ellipse2D$Double. x y (* 2 rad) (* 2 rad))))))

(defn draw-line [g [x1 y1] [x2 y2] color]
  (doto g
    (.setColor color)
    (.drawLine x1 y1 x2 y2)))

(defn collision? [g state]
  (let [{[ax ay] :a [bx by] :b} @state
	obstacles (dissoc @state :a :b)]
    (some false? 
	  (map #(let [[id [ox oy or]] %] 
		  (draw-object 
		   g (closest-point-on-line [ax ay] [bx by] [ox oy]) 10
		   java.awt.Color/RED)
		  (path-clean [ax ay] [bx by] [ox oy] or))
	       obstacles))))

(defn board [state]
  (proxy [javax.swing.JPanel] []
    (paintComponent
     [g]
     (.setColor g java.awt.Color/WHITE)
     (.fillRect g 0 0 (.getWidth this) (.getHeight this))
     (let [{a :a b :b} @state]
       (.setStroke 
       	g (java.awt.BasicStroke. (float 5.0)
       				 java.awt.BasicStroke/CAP_BUTT 
       				 java.awt.BasicStroke/JOIN_BEVEL
       				 (float 1.0) 
       				 (float-array [8.0 3.0 2.0 3.0])
       				 (float 0)))
       (draw-line g a b 
		  (if (collision? g state)
		    java.awt.Color/RED java.awt.Color/BLACK)))
     (.setStroke g (java.awt.BasicStroke.))
     (doseq [[id pos] @state] 
       (cond (or (= id :a)
		 (= id :b)) (draw-object g pos 20 java.awt.Color/BLUE)
		 :default (do (draw-object g pos 20 java.awt.Color/GREEN)
			      (draw-bounds g pos java.awt.Color/GREEN)))))))

(defn mouse-in-range? [u v]
  (let [[ux uy] u [vx vy] v]
    (if (and (and (> ux (- vx 30)) (< ux (+ vx 30)))
	     (and (> uy (- vy 30)) (< uy (+ vy 30)))) true false)))

(defn mouse-pressed-on [state x y]
  (first
   (filter #(true? (second %)) 
	   (map #(let [[id pos] %] 
		   [id (mouse-in-range? [x y] pos)]) @state))))

(defn mouse-listener [state]
  (let [dragging (ref nil)]
    (proxy [java.awt.event.MouseAdapter 
	    java.awt.event.MouseMotionListener] [] 
      (mousePressed 
       [e]
       (dosync (ref-set dragging 
			(mouse-pressed-on state (.getX e) (.getY e)))))
      (mouseReleased [e] (dosync (ref-set dragging nil)))
      (mouseDragged 
       [e]
       (if (not (nil? @dragging))
	 (let [[id _] @dragging
	       radius (last (id @state))]
	   (dosync 
	    (alter state assoc-in [id] [(.getX e) (.getY e) radius]))))))))

(defn frame []
  (let [state (ref {:a [22 22] :b [445 250] 
		    :c [379 90 60] :d [135 223 120] :e [560 290 80]})
	board (board state)
	mouse-listener (mouse-listener state)]
    (.addMouseListener board mouse-listener)
    (.addMouseMotionListener board mouse-listener)
    (add-watch state "update" (fn [k r o b] (.repaint board)))
    (doto (javax.swing.JFrame.)
      (.setAlwaysOnTop true)
      (.add board)
      (.setSize 650 400)
      (.setVisible true))))

(frame)
 