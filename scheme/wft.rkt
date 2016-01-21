(require plot)
(require (planet neil/csv:2:0))
(require srfi/1)

(define comp (csv->list (open-input-file "C:/Users/SamirM1/Desktop/competency2016-01-18.csv")))
;(define trn (csv->list (open-input-file "training8.csv")))

(car comp)
;(car trn)
;(length trn)

(head comp)

(define nth (lambda (ls n)
      (if (eq? n 1)
          ; this is the trivial case
          (car ls)
          ; otherwise find the result in the tail of ls
          (nth (cdr ls) (- n 1))))) 

(nth (apply zip comp) 12)


(define bml 
  (length 
   (filter (lambda (n) (equal? n "SLS Basic Mud Logging Course")) 
           (cdr (nth (apply zip trn) 11)))))


(define total (length comp))

(define complete 
  (length 
   (filter (lambda (n) (equal? n "100")) 
           (cdr (nth (apply zip comp) 12)))))

(define zero
  (length 
   (filter (lambda (n) (equal? n "0")) 
           (cdr (nth (apply zip comp) 12)))))

;(println "Total Assigned: " total)
;(println "Complete Assigned: " complete) 
;(println "In Progress:" progress)
;(write "Didn't Start: ")
(write zero)
;(display "dd" zero)

;(plot (function sin (- 10) 10))
