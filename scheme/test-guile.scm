#!/usr/local/bin/guile -e main -s
!#

(define (main args)
    (map (lambda (arg) (display arg) (display  " "))
         args)
    (newline))

(main '(0 1 2 3 4 5))

(define (fact n)
  (if (< n 1) 1
      (* n (fact (- n 1)))))

(fact 100)

; read-lines [port-or-filename] -- defaults to current input
(define (read-lines . args)
  (let ((p (cond ((null? args) (current-input-port))
                 ((port? (car args)) (car args))
                 ((string? (car args)) (open-input-file (car args)))
                 (else (error 'read-lines "bad argument")))))
    (let loop ((line (read-line p)) (lines (list)))
      (if (eof-object? line)
          (begin (if (and (pair? args) (string? (car args)))
                   (close-input-port p))
                 (reverse lines))
          (loop (read-line p) (cons line lines))))))

;works in Chicken, Racket, SISC
;Read a file to a list of chars
(define (file->char_list path)
 (call-with-input-file path
   (lambda (input-port)
     (let loop ((x (read-char input-port)))
       (cond 
        ((eof-object? x) '())
        (#t (begin (cons x (loop (read-char input-port))))))))))
; This function is reasonably fast and portable across implementations. 
; All that is needed is to convert the char_list to a string.
; The simplest way is:
;may not work if there is limit on arguments
(apply string (file->char_list "mydata.txt"))
