#lang racket
(require plot)

(plot (function sin pi (- pi)))

(plot3d (surface3d (λ (x y) (* (cos x) (sin y)))
                     (- pi) pi (- pi) pi)
          #:title "An R × R → R function"
          #:x-label "x" #:y-label "y" #:z-label "cos(x) sin(y)")

(parameterize ([plot-title  "An R × R → R function"]
                 [plot-x-label "x"]
                 [plot-y-label "y"]
                 [plot-z-label "cos(x) sin(y)"])
    (plot3d (contour-intervals3d (λ (x y) (* (cos x) (sin y)))
                                 (- pi) pi (- pi) pi)))