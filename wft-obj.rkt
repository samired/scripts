#lang racket
;; A picture

(require plot)
(require (planet neil/csv:2:0))
(require srfi/1)

(define file (csv->list (open-input-file "C:/Users/SamirM1/Desktop/objectives.csv")))

(car file)

(length (list-ref (apply zip file) 8))

(length  (filter 
          (lambda (n)(equal?  "Weatherford Competence Assurance Process (WCAP)" n))
          file))


(define (assigned obj file) 
  (length 
   (filter (lambda (n) (equal? n obj)) 
           (cdr (list-ref (apply zip file) 8)))))

(assigned "Weatherford Competence Assurance Process (WCAP)" file)

(assigned "Weatherford CORE /– QHSSE eAssessment" file)


(define (met file)
  (length 
   (filter (lambda (n) (equal? n "Met")) 
           (cdr (list-ref (apply zip file) 12)))))

(define (in-progress file)
  (length 
   (filter (lambda (n) (equal? n "In Progress")) 
           (cdr (list-ref (apply zip file) 12)))))

