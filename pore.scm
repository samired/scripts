;; Show current date and time
(use-modules (srfi srfi-19))


(display (date->string (current-date) "~A, ~B ~e ~Y ~H:~S"))
(newline)
(display "RPM: ")
(define rpm (read))

(display "WOB: ")
(define wob  (read))

(display "ROP: ")
(define rop  (read))

(display "Bit Diameter: ")
(define bit  (read))

(display "Mud Weight: ")
(define mwt  (read))

(display "Normal Pore Pressure: ")
(define npp  (read))

(define (dx rpm wob rop bit  mwt) 
 (/ (log10 (/ rop (* 60 rpm)))
     (log10 (/ (* 12 wob) (* 1000 bit)))))

(define dx-value (dx rpm wob rop bit mwt)) 

(display 
 (string-append 
  "Dx is:  " (number->string dx-value)))


 
