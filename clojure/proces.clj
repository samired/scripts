(use '(incanter core processing))

(let [radius (ref 50.0)
      X (ref nil)
      Y (ref nil)
      nX (ref nil)
      nY (ref nil)
      delay 16
	  sktch (sketch
        (setup []
          (doto this
            (size 600 600)
            (stroke-weight 10)
            (framerate 75)
            smooth)
          (dosync
            (ref-set X (/ (width this) 2))
            (ref-set Y (/ (width this) 2))
            (ref-set nX @X)
            (ref-set nY @Y)))
			(draw []
				(dosync
					(ref-set radius (+ @radius
                        (sin (/ (frame-count this) 4))))
						(ref-set X (+ @X (/ (- @nX @X) delay)))
						(ref-set Y (+ @Y (/ (- @nY @Y) delay))))
				(doto this
					(background 125) ;; gray
					(fill 0 121 184)
					(stroke 255)
					(ellipse @X @Y @radius @radius)))
			(mouseMoved [mouse-event]
  (dosync
    (ref-set nX (mouse-x mouse-event))
    (ref-set nY (mouse-y mouse-event)))))]
	
(view sktch :size [500 500]))
