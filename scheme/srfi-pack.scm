;; required for syntax-rules, but loses all source code information
;; TODO: should be loaded only when a SRFI really needs it
(load "~~/syntax-case.scm")

;; REQUIRE-EXTENSION is the mechanism used to make a SRFI available
;; TODO: fix, this currently doesn't interact correctly with SRFI-0
(load "~~/srfi-pack/srfi-55.scm")

;; srfi-0 is native
;; TODO: fix, it doesn't work with packages loaded with REQUIRE-EXTENSION...
(register-extension '(srfi 0) (lambda () 'native))

;; srfi-1
;; TODO: fix the :OPTIONAL and LET-OPTIONAL uses
(register-extension '(srfi 1) (lambda () (load "~~/srfi-pack/srfi-1")))

;; srfi-2 is not supported yet

;; srfi-4 is native
(register-extension '(srfi 4) (lambda () 'native))

;; srfi-5 is not supported yet
;; TODO: take care of the 'unbound STANDARD-LET' error
;; (register-extension '(srfi 5) (lambda () (load "~~/srfi-pack/srfi-5")))

;; srfi-6 is native
(register-extension '(srfi 6) (lambda () 'native))

;; srfi-7 is not supported

;; srfi-8 is native
(register-extension '(srfi 8) (lambda () 'native))

;; srfi-9 is native
(register-extension '(srfi 9) (lambda () 'native))

;; srfi-10 is not supported yet

;; srfi-11
(register-extension '(srfi 11) (lambda () (load "~~/srfi-pack/srfi-11")))

;; srfi-13
(register-extension '(srfi 13) (lambda () (load "~~/srfi-pack/srfi-13")))

;; srfi-14 is not supported yet
;; (load "~~/srfi-pack/srfi-14")

;; srfi-16
(register-extension '(srfi 16) (lambda () (load "~~/srfi-pack/srfi-16")))

;; srfi-17 is not supported

;; srfi-18 is native
(register-extension '(srfi 18) (lambda () 'native))

;; srfi-19 is not supported yet (conflict with TIME)
;(register-extension '(srfi 19) (lambda () (load "~~/srfi-pack/srfi-19")))

;; srfi-21 is native
(register-extension '(srfi 21) (lambda () 'native))

;; srfi-22 is native
(register-extension '(srfi 22) (lambda () 'native))

;; srfi-23 is native
(register-extension '(srfi 23) (lambda () 'native))

;; srfi-25
(register-extension '(srfi 25) (lambda () (load "~~/srfi-pack/srfi-25")))

;; srfi-26
(register-extension '(srfi 26) (lambda () (load "~~/srfi-pack/srfi-26")))

;; srfi-27 is native
(register-extension '(srfi 27) (lambda () 'native))

;; srfi-28
(register-extension '(srfi 28) (lambda () (load "~~/srfi-pack/srfi-28")))

;; srfi-29 is not supported yet

;; srfi-30 is native
(register-extension '(srfi 30) (lambda () 'native))

;; srfi-31
(register-extension '(srfi 31) (lambda () (load "~~/srfi-pack/srfi-31")))

;; srfi-34
(register-extension '(srfi 34) (lambda () (load "~~/srfi-pack/srfi-34")))

;; TODO: fix SRFI-1 so this works
;; srfi-35 is not supported yet
(register-extension '(srfi 35) (lambda () (load "~~/srfi-pack/srfi-35")))

;; srfi-36 is not supported yet

;; srfi-37
(register-extension '(srfi 37) (lambda () (load "~~/srfi-pack/srfi-37")))

;; srfi-38 
;; TODO: determine if Gambit doesn't provide this already
(register-extension '(srfi 38) (lambda () (load "~~/srfi-pack/srfi-38")))

;; srfi-39 is native
(register-extension '(srfi 39) (lambda () 'native))

;; srfi-40
(register-extension '(srfi 40) (lambda () (load "~~/srfi-pack/srfi-40")))

;; srfi-42
(register-extension '(srfi 42) (lambda () (load "~~/srfi-pack/srfi-42")))

;; srfi-43
(register-extension '(srfi 43) (lambda () (load "~~/srfi-pack/srfi-43")))

;; srfi-44 is not supported yet

;; srfi-45
(register-extension '(srfi 45) (lambda () (load "~~/srfi-pack/srfi-45")))

;; srfi-47
;; TODO: remove slib stuff
;;(load "~~/srfi-pack/srfi-47")

;; srfi-48 isn't supported

;; srfi-51
(register-extension '(srfi 51) (lambda () (load "~~/srfi-pack/srfi-51")))

;; srfi-54 isn't supported yet
;; TODO: fix ref. impl. ??
;; (register-extension '(srfi 54) (lambda () (load "~~/srfi-pack/srfi-54")))


;; srfi-55
;; see top of file :)
(register-extension '(srfi 55) (lambda () 'duh))
