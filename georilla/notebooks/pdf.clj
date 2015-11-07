;; gorilla-repl.fileformat = 1

;; **
;;; # PDFer
;; **

;; @@
(ns pdfer
  (:require 
    [gorilla-plot.core :as plot]
    [pdfboxing.merge :as pdf]
    [pdfboxing.text :as text]
    [pdfboxing.form :as form]
    [pdfboxing.info :as info])
  (:import
    [org.apache.pdfbox/pdfbox]
    ))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ###PDF Merge
;; **

;; @@
; needs some trials
;(.PDFMerger ["pdf/0.pdf" "pdf/1.pdf" "pdf/2.pdf" "pdf/3.pdf" "pdf/4.pdf" "pdf/5.pdf" "pdf/6.pdf" "pdf/7.pdf" "pdf/8.pdf" "pdf/9.pdf" "pdf/10.pdf"] "fo.pdf")
;; @@

;; @@
(pdf/merge-pdfs :input ["pdf/A (1).pdf" "pdf/A (2).pdf" "pdf/A (3).pdf" "pdf/A (4).pdf" "pdf/A (5).pdf" "pdf/A (6).pdf" "pdf/A (7).pdf" "pdf/A (8).pdf" "pdf/A (9).pdf" "pdf/A (10).pdf" "pdf/A (11).pdf" "pdf/A (12).pdf" "pdf/A (13).pdf"] 
                :output "fooo.pdf")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### Extract text
;; **

;; @@
(text/extract "test/pdfs/hello.pdf")
;; @@

;; **
;;; 
;;; ###List form fields of a PDF
;;; 
;;; To list fields and values:
;; **

;; @@
(form/get-fields "pdfs/interactiveform.pdf"))
{"Emergency_Phone" "", "ZIP" "", "COLLEGE NO DEGREE" "", ...}
;; @@

;; **
;;; ###Fill in PDF forms
;;; 
;;; To fill in form's field supply a hash map with field names and desired values. It will create a copy of fillable.pdf as new.pdf with the fields filled in:
;; **

;; @@
(form/set-fields "pdfs/fillable.pdf" "pdfs/new.pdf" {"Text10" "My first name"})
;; @@

;; **
;;; ###Rename form fields of a PDF
;;; 
;;; To rename PDF form fields, supply a hash map where the keys are the current names and the values new names:
;; **

;; @@
(form/rename-fields "test/pdfs/interactiveform.pdf" "test/pdfs/addr1.pdf" {"Address_1" "NewAddr"})
;; @@

;; **
;;; ###Get page count of a PDF document
;; **

;; @@
(info/page-numbers "test/pdfs/interactiveform.pdf")
;; @@

;; **
;;; ###Get info about a PDF document
;;; 
;;; Such as title, author, subject, keywords, creator & producer
;; **

;; @@
(info/about-doc "test/pdfs/interactiveform.pdf")
;; @@
