;; gorilla-repl.fileformat = 1

;; **
;;; ## Training Analysis
;; **

;; @@
(ns training
 (:use  [incanter core io charts excel stats datasets]
        [incanter-gorilla.render]
        [gorilla-repl latex]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def data (read-dataset "./data/trn.csv" :header true))
(col-names data)
(get-categories "Country" data)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;QATAR&quot;</span>","value":"\"QATAR\""},{"type":"html","content":"<span class='clj-string'>&quot;YEMEN&quot;</span>","value":"\"YEMEN\""},{"type":"html","content":"<span class='clj-string'>&quot;EGYPT&quot;</span>","value":"\"EGYPT\""},{"type":"html","content":"<span class='clj-string'>&quot;OMAN&quot;</span>","value":"\"OMAN\""},{"type":"html","content":"<span class='clj-string'>&quot;KUWAIT&quot;</span>","value":"\"KUWAIT\""},{"type":"html","content":"<span class='clj-string'>&quot;TURKMENISTAN&quot;</span>","value":"\"TURKMENISTAN\""},{"type":"html","content":"<span class='clj-string'>&quot;UNITED ARAB EMIRATES&quot;</span>","value":"\"UNITED ARAB EMIRATES\""},{"type":"html","content":"<span class='clj-string'>&quot;SAUDI ARABIA&quot;</span>","value":"\"SAUDI ARABIA\""},{"type":"html","content":"<span class='clj-string'>&quot;INDIA&quot;</span>","value":"\"INDIA\""},{"type":"html","content":"<span class='clj-string'>&quot;ALGERIA&quot;</span>","value":"\"ALGERIA\""},{"type":"html","content":"<span class='clj-string'>&quot;IRAQ&quot;</span>","value":"\"IRAQ\""},{"type":"html","content":"<span class='clj-string'>&quot;MOROCCO&quot;</span>","value":"\"MOROCCO\""}],"value":"#{\"QATAR\" \"YEMEN\" \"EGYPT\" \"OMAN\" \"KUWAIT\" \"TURKMENISTAN\" \"UNITED ARAB EMIRATES\" \"SAUDI ARABIA\" \"INDIA\" \"ALGERIA\" \"IRAQ\" \"MOROCCO\"}"}
;; <=

;; @@
(def country "EGYPT")
(def course "SLS Basic Mud Logging Course")

  
(nrow (sel data :filter #(= (nth % 10) course)))

(nrow (sel data :filter #(= (nth % 6) country)))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>648</span>","value":"648"}
;; <=

;; @@
(print 
  ($ [0 1 2] ($where {:Course course :Country country} data)))
;; @@
;; ->
;;; 
;;; | :Learner-Id |   :First-Name | :Last-Name |
;;; |-------------+---------------+------------|
;;; |      120253 |         SOBHY |      FOUDA |
;;; |      120264 |       HAYTHAM |     ROSHDY |
;;; |      121655 |       MOHAMED |    MAHFOUZ |
;;; |      160991 | MAHMOUD AHMED |    ABDALLA |
;;; |      178769 |         AHMED |    BARAKAT |
;;; |      208053 |        HOSSAM |    ELBADRY |
;;; |      208240 |         AHMED |      AFIFI |
;;; |      208241 |         WALID | AWAD ALLAH |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ###Analysis of Mandatory Courses
;; **

;; @@
(def bml (nrow (sel data :filter #(= (nth % 10) "SLS Basic Mud Logging Course"))))

(def aml (nrow (filter #(= % "SLS Advanced Mud Logging Course") ($ :Course data))))

(def anax (nrow (filter #(= % "SLS Wellsite Systems Operator") ($ :Course data))))

(def hei (nrow (sel data :filter #(= (nth % 10) "SLS Hydrocarbon Evaluation and Interpretation"))))

(def exprotect(nrow (filter #(= % "Introduction to Ex Protection") ($ :Course data))))

(def coshh(count (filter #(= % "Introduction to COSHH") ($ :Course data))))

(def elect (count (filter #(= % "Introduction to Electricity") ($ :Course data))))

(chart-view (bar-chart ["BML" "AML" "ANAX" "HEI" "EX" "ELEC" "COSHH"]
                       [bml aml anax hei exprotect elect coshh]))

(print "BML: " bml "\nAML: " aml "\nANAX:" anax "\nHEI: " hei "\nEx Protection: " exprotect "\nCOSHH: " coshh "\nElectricity: " elect)

;; @@
;; ->
;;; BML:  233 
;;; AML:  83 
;;; ANAX: 134 
;;; HEI:  43 
;;; Ex Protection:  325 
;;; COSHH:  264 
;;; Electricity:  237
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(print 
  ($ [1 10] ($where {:Learner-Id 120320} data)))
;; @@
;; ->
;;; 
;;; | :First-Name |                                                                                               :Course |
;;; |-------------+-------------------------------------------------------------------------------------------------------|
;;; |        EHAB |                                                                            Basic Interpersonal Skills |
;;; |        EHAB |                                                                  Essentials for Enterprise Excellence |
;;; |        EHAB |                                                                             Influential Presentations |
;;; |        EHAB |                                                                           Introduction to Electricity |
;;; |        EHAB |                                                                             MENA IT Induction Program |
;;; |        EHAB |                                                                          SLS Basic Mud Logging Course |
;;; |        EHAB |                                                                       SLS Advanced Mud Logging Course |
;;; |        EHAB |                                              SLS Introduction to Formation Pressure Evaluation Course |
;;; |        EHAB |                                                                               Environmental Induction |
;;; |        EHAB |                                                                 Weatherford Health &amp; Safety Induction |
;;; |        EHAB |                                                           Weatherford Global Defensive Driving Online |
;;; |        EHAB |                                                                             EEP Competency Assessment |
;;; |        EHAB |                                                                           QHSSE Competency Assessment |
;;; |        EHAB |                                           8141 - International Bribery and Corruption: Global Edition |
;;; |        EHAB |                                                       Weatherford Anti-Corruption Compliance e-Policy |
;;; |        EHAB |                                                                 Weatherford Trade Compliance e-Policy |
;;; |        EHAB |                                                             Weatherford Antitrust Compliance e-Policy |
;;; |        EHAB |                                                                                 Introduction to COSHH |
;;; |        EHAB |                                                            Weatherford Conflicts of Interest e-Policy |
;;; |        EHAB |                                                         Subsurface Evaluation Applications: Geology 1 |
;;; |        EHAB |                                                 Weatherford Anti-Money Laundering Compliance e-Policy |
;;; |        EHAB | Policy on Use of Computer Systems and Data Assets for Global Weatherford employees (Policy G of G-07) |
;;; |        EHAB |                                                                     IT Helpdesk Management - MEMOcast |
;;; |        EHAB |                                                                         Introduction to Ex Protection |
;;; |        EHAB |                                                        Introduction to Petroleum - Module 2 - Geology |
;;; |        EHAB |                                        HSE e-Policy: Driver and Vehicle Safety GEM - Rules to Live By |
;;; |        EHAB |                                      HSE e-Policy: Commitment and Intervention GEM - Rules to Live By |
;;; |        EHAB |                                                  HSE e-Policy: Facility Safety GEM - Rules to Live By |
;;; |        EHAB |                                           HSE e-Policy: Induction and Training GEM - Rules to Live By |
;;; |        EHAB |                                                  HSE e-Policy: Risk Management GEM - Rules to Live By |
;;; |        EHAB |                                                      Weatherford Qualified Competence Assessor Course |
;;; |        EHAB |                                                      Weatherford Qualified Competence Assessor Course |
;;; |        EHAB |                                                                                  Introduction to OEPS |
;;; |        EHAB |                                 HSE e-Policy: Lifting Equipment and Operations GEM - Rules to Live By |
;;; |        EHAB |                                             HSE e-Policy: Hazardous Substances GEM - Rules to Live By |
;;; |        EHAB |                                           HSE e-Policy: Hazardous Environments GEM - Rules to Live By |
;;; |        EHAB |                                                                                SLS WellWizard� Update |
;;; |        EHAB |                                                          HSE e-Policy: Four Tenets - Rules to Live By |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
;(save ($where {:Country "EGYPT"} data) "C:\\EGYPT.csv")
;; @@
