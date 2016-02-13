;;; @Package     octave.scm
;;; @Subtitle    A simple interface to GNU/Octave
;;; @HomePage    http://carretechnologies.com/scheme/octave/octave.html
;;; @Author      Pierre-Alexandre Fournier
;;; @AuthorEmail octave@@carretechnologies.com
;;; @Version     0.6
;;; @Date        April 11th 2008

;; $Id:  $


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Octave interface version
(define (octave:version) "0.6")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Octave ports and pid
(define octave:input-port  #f)
(define octave:output-port #f)
(define octave:pid         #f)


;;; Compatibility layer for Chicken and Gambit
(cond-expand
 ;;;;;;;;;;;;;;;;;;;;;; Chicken ;;;;;;;;;;;;;;;;;;;;;;;
 (chicken
  (declare (export 
	    ;; base variables and functions
	    octave:version
	    octave:input-port
	    octave:output-port
	    octave:pid
	    octave:start
	    octave:stop
	    octave:send
	    ;; 2d plot functions
	    octave:plot
	    octave:semilogx
	    octave:semilogy
	    octave:loglog
	    octave:polar
	    octave:bar
	    octave:stairs
	    octave:errorbar
	    octave:mesh-xyz
	    ;; 3d plot functions
	    octave:mesh
	    octave:contour
	    octave:imagesc
	    ;; Labels
	    octave:title
	    octave:xlabel
	    octave:ylabel
	    octave:legend
	    octave:grid
	    ;; Other
	    octave:figure
	    octave:replot
	    octave:close
	    ;; Files
	    octave:supported-file-types
	    octave:save-plot-to
	    ))

  (use posix)

  (define octave:conc conc)
  (define octave:string-intersperse string-intersperse)


  ;;; Start octave process
  (define (octave:start)
    (let-values ((octave:process-info (process "octave -q")))
		(set! octave:input-port  (car   octave:process-info))
		(set! octave:output-port (cadr  octave:process-info))
		(set! octave:pid         (caddr octave:process-info))))


  ;;; Stop octave process
  (define (octave:stop)
    (octave:send "quit")
    (set! octave:input-port  #f)
    (set! octave:output-port #f)
    (set! octave:pid         #f))
  ;;; Send a command to Octave
  (define (octave:send str)
    (cond ((not octave:output-port)
	   (error "Octave process not started. Start it with (octave:start).\n"))
	  (else
	   (display str octave:output-port))))
  );;; End Chicken

 ;;;;;;;;;;;;;;;;;;;;;; Gambit ;;;;;;;;;;;;;;;;;;;;;;;
 (gambit
  (define (octave:conc . objs)
    (with-output-to-string 
      "" (lambda () (display objs))))

  (define (octave:string-intersperse lst between)
    (cond ((null? lst)
	   lst)
	  (else
	   (let loop ((acc (octave:conc (car lst)))
		      (lst (cdr lst)))
	     (cond ((null? lst) 
		    acc)
		   (else
		    (loop (octave:conc acc between (car lst))
			  (cdr lst))))))))

  ;;; Start octave process
  (define (octave:start)
    (set! octave:input-port  
	  (open-process (list path: "octave"
			      arguments: (list "-q"))))
    (set! octave:output-port octave:input-port)
    (set! octave:pid         #t))

  ;;; Stop octave process
  (define (octave:stop)
    (octave:send "quit")
    (close-input-port  octave:input-port)
    (close-output-port octave:output-port)
    (set! octave:input-port  #f)
    (set! octave:output-port #f)
    (set! octave:pid         #f))

  ;;; Send a command to Octave
  (define (octave:send str)
    (cond ((not octave:output-port)
	   (error "Octave process not started. Start it with (octave:start).\n"))
	  (else
	   (display str octave:output-port)
	   (force-output octave:output-port))))
  );;; End Gambit

 ;;;;;;;;;;;;;;;;;;;;;; Bigloo ;;;;;;;;;;;;;;;;;;;;;;;
 (bigloo

  (define (octave:conc . objs)
    (with-output-to-string 
	(lambda () (map display objs))))

  (define (octave:string-intersperse lst between)
    (cond ((null? lst)
	   lst)
	  (else
	   (let loop ((acc (octave:conc (car lst)))
		      (lst (cdr lst)))
	     (cond ((null? lst) 
		    acc)
		   (else
		    (loop (octave:conc acc between (car lst))
			  (cdr lst))))))))
    
  ;;; Start octave process
  (define (octave:start)
    (let ((proc (run-process "octave" "-q" input: pipe:)))
      ;;; scheme output goes into process input
      (set! octave:input-port  (process-output-port proc))
      (set! octave:output-port (process-input-port  proc))
      (set! octave:pid         (process-pid         proc))))

  ;;; Stop octave process
  (define (octave:stop)
    (octave:send "quit")
    (close-input-port  octave:input-port)
    (close-output-port octave:output-port)
    (set! octave:input-port  #f)
    (set! octave:output-port #f)
    (set! octave:pid         #f))

  ;;; Send a command to Octave
  (define (octave:send str)
    (cond ((not octave:output-port)
	   (error "octave:send" 
	     "Octave process not started. Start it with (octave:start).\n" 
	     (octave:conc (substring str 0 30) "...")))
	  (else
	   (display str octave:output-port)
	   (flush-output-port octave:output-port))))
  ));;; End Bigloo


(define (octave:restart)
  (octave:stop)
  (octave:start))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; some utilities
(define (list->octave-vector lst)
  (octave:conc "[" (octave:string-intersperse (map octave:conc lst) " ") "]"))

(define (list->octave-matrix lst)
  (octave:conc "[" (octave:string-intersperse (map list->octave-vector lst) "; ") "]"))

;;; verify arguments length for 2d plots
(define (assert-args-lengths args)  
  (cond ((null? args)
	 #t)
	((null? (cdr args))
	 #t)
	((string? (cadr args))
	 (assert-args-lengths (cddr args)))
	((and (> (length args) 3)
	      (string? (caddr args))
	      (= (length (car  args))
		 (length (cadr args))))
	 (assert-args-lengths (cdddr args)))
	((= (length (car  args))
	    (length (cadr args)))
	 (assert-args-lengths (cddr args)))
	(else
	 #f)))

(define (args->comma-separated-octave-vectors args)
  (let ((octave-vectors
	 (map (lambda (arg)
		(cond ((list? arg)
		       (list->octave-vector arg))
		      (else
		       (octave:conc arg))))
	      args)))
    (octave:string-intersperse octave-vectors ", ")))

(define (args->comma-separated-octave-matrixes args)
  (let ((octave-matrixes
	 (map (lambda (arg)
		(cond ((list? arg)
		       (list->octave-matrix arg))
		      (else
		       (octave:conc arg))))
	      args)))
    (octave:string-intersperse octave-matrixes ", ")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Plots

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; graph lists of numbers in gnuplot using a 2d plot function (e.g.:
;;; "plot", "loglog", "semilogx")
(define (octave:generic-2dplot plot-function args)
  (cond ((null? args)
         #f)
        ((not (assert-args-lengths args))
	 (error "vector lengths must match"))
	(else
         (octave:send
          (octave:conc plot-function
                "(" (args->comma-separated-octave-vectors args) ");\n")))))

;;; some 2d plot functions
(define (octave:plot     . args) (octave:generic-2dplot "plot"     args))
(define (octave:semilogx . args) (octave:generic-2dplot "semilogx" args))
(define (octave:semilogy . args) (octave:generic-2dplot "semilogy" args))
(define (octave:loglog   . args) (octave:generic-2dplot "loglog"   args))
(define (octave:polar    . args) (octave:generic-2dplot "polar"    args))
(define (octave:bar      . args) (octave:generic-2dplot "bar"      args))
;;; stairs is similar to bar
(define (octave:stairs   . args) (octave:generic-2dplot "stairs"   args))

(define (octave:errorbar . args) (octave:generic-2dplot "errorbar" args))


(define (octave:mesh-xyz . args)
  (octave:send
   (octave:conc "mesh "
	  "(" (args->comma-separated-octave-vectors 
	       (list (car args) (cadr args)))
	  "," (args->comma-separated-octave-matrixes 
	       (list (list-ref args 2)))
	  ");\n")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; graph lists of lists of numbers in gnuplot using a 3d plot
;;; function (e.g.: "mesh")
(define (octave:generic-3dplot plot-function args)
  (cond
   ((null? args)
    #f)
   (else
    (octave:send
     (octave:conc plot-function
           "(" (args->comma-separated-octave-matrixes args) ");\n")))))

;;; some 3d plot functions
(define (octave:mesh     . args) (octave:generic-3dplot "mesh"     args))
(define (octave:contour  . args) (octave:generic-3dplot "contour"  args))
(define (octave:imagesc  . args) (octave:generic-3dplot "imagesc"  args))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; new octave figure
(define (octave:figure)  (octave:send "figure;\n"))

;;; close all figures
(define (octave:close)  (octave:send "close;\n"))
(define (octave:close-all)  (octave:send "close all;\n"))

;;; example of wrapper function
(define (octave:vct-plot . args)
  (apply octave:plot (map vct->list args)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Labels

;;; title, x and y labels
(define (octave:title  title)  
  (octave:send (octave:conc "title('"  title  "');\n")))
(define (octave:xlabel xlabel) 
  (octave:send (octave:conc "xlabel('" xlabel "');\n")))
(define (octave:ylabel ylabel) 
  (octave:send (octave:conc "ylabel('" ylabel "');\n")))

;;; legend only works with plot, bar
(define (octave:legend . args) 
  (define (quote-args args)
    (octave:conc (octave:string-intersperse
	   (map (lambda (s) (octave:conc "'" s "'")) args)
	   ", ")))
  (octave:send (octave:conc "legend(" (quote-args args) ");\n")))

;;; grid on/off
(define (octave:grid . args)
  (let ((off? (cond ((and (not (null? args)) 
                          (string=? (car args) "off"))
                     "off")
                    (else
                     ""))))
    (octave:send (octave:conc "grid " off? ";\n"))))

;;; replot
(define (octave:replot)  
  (octave:send "replot;\n"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; GNUPLOT output file types

;;; set plot output
(define (octave:set-terminal! output)
  ;;; __gnuplot_set__ is an internal Octave function that replaces
  ;;; gset (gset is deprecated)
  (octave:send (octave:conc "__gnuplot_set__ terminal " output ";\n")))

;;; reset plot output
(define (octave:reset-terminal!) (octave:set-terminal! "x11"))

;;; set output
(define (octave:set-output! output)
  (octave:send (octave:conc "__gnuplot_set__ output " output ";\n")))

;;; set output file
(define (octave:set-output-file! filename)
  (octave:set-output! (octave:conc "'" filename "'")))

(define (octave:reset-output!)
  (octave:set-output! ""))

;;; All File Types supported by gnuplot 4.0 patchlevel 0
(define octave:supported-file-types
  (list
   "aed512" ;;  AED 512 Terminal
   "aed767" ;;  AED 767 Terminal
   "aifm" ;;  Adobe Illustrator 3.0 Format
   "bitgraph" ;;  BBN Bitgraph Terminal
   "cgm" ;;  Computer Graphics Metafile
   "corel" ;;  EPS format for CorelDRAW
   "dumb" ;;  printer or glass dumb terminal
   "dxf" ;;  dxf-file for AutoCad (default size 120x80)
   "eepic" ;;  EEPIC -- extended LaTeX picture environment
   "emf" ;;  Enhanced Metafile format
   "emtex" ;;  LaTeX picture environment with emTeX specials
   "epslatex" ;;  LaTeX (Text) and encapsulated PostScript
   "epson" ;;_180dpi  Epson LQ-style 180-dot per inch (24 pin) printers
   "epson" ;;_60dpi  Epson-style 60-dot per inch printers
   "epson" ;;_lx800  Epson LX-800, Star NL-10, NX-1000, PROPRINTER ...
   "fig" ;;  FIG graphics language for XFIG graphics editor
   "gif" ;;  GIF format [mode] [fontsize] [size] [colors]
   "gpic" ;;  GPIC -- Produce graphs in groff using the gpic preprocessor
   "hp2623A" ;;  HP2623A and maybe others
   "hp2648" ;;  HP2648 and HP2647
   "hp500c" ;;  HP DeskJet 500c, [75 100 150 300] [rle tiff]
   "hpdj" ;;  HP DeskJet 500, [75 100 150 300]
   "hpgl" ;;  HP7475 and relatives [number of pens] [eject]
   "hpljii" ;;  HP Laserjet series II, [75 100 150 300]
   "hppj" ;;  HP PaintJet and HP3630 [FNT5X9 FNT9X17 FNT13X25]
   "imagen" ;;  Imagen laser printer
   "jpeg" ;;  JPEG images using libgd and TrueType fonts
   "kc"	;;_tek40xx  MS-DOS Kermit Tek4010 terminal emulator - color
   "km"	;;_tek40xx  MS-DOS Kermit Tek4010 terminal emulator - monochrome
   "latex" ;;  LaTeX picture environment
   "mf"	;;  Metafont plotting standard
   "mif" ;;  Frame maker MIF 3.00 format
   "mp"	;;  MetaPost plotting standard
   "nec" ;;_cp6  NEC printer CP6, Epson LQ-800 [monocrome color draft]
   "okidata" ;;  OKIDATA 320/321 Standard
   "pbm" ;;  Portable bitmap [small medium large] [monochrome gray color]
   "pcl5" ;;  HP Designjet 750C, HP Laserjet III/IV, etc. (many options)
   "png" ;;  PNG images using libgd and TrueType fonts
   "postscript"	;;  PostScript graphics language [mode "fontname" font_size]
   "pslatex" ;;  LaTeX picture environment with PostScript \specials
   "pstex" ;;  plain TeX with PostScript \specials
   "pstricks" ;;  LaTeX picture environment with PSTricks macros
   "qms" ;;  QMS/QUIC Laser printer (also Talaris 1200 and others)
   "regis" ;;  REGIS graphics language
   "selanar" ;;  Selanar
   "starc" ;;  Star Color Printer
   "svg" ;;  W3C Scalable Vector Graphics driver
   "table" ;;  Dump ASCII table of X Y [Z] values to output
   "tandy" ;;_60dpi  Tandy DMP-130 series 60-dot per inch graphics
   "tek40xx" ;;  Tektronix 4010 and others; most TEK emulators
   "tek410x" ;;  Tektronix 4106, 4107, 4109 and 420X terminals
   "texdraw" ;;  LaTeX texdraw environment
   "tgif" ;;  TGIF X11 [mode] [x,y] [dashed] ["font" [fontsize]]
   "tkcanvas" ;;  Tk/Tcl canvas widget [perltk] [interactive]
   "tpic" ;;  TPIC -- LaTeX picture environment with tpic \specials
   "unknown" ;;  Unknown terminal type - not a plotting device
   "vttek" ;;  VT-like tek40xx terminal emulator
   "x11" ;;  X11 Window System
   "X11" ;;  X11 Window System (identical to x11)
   "xlib" ;;  X11 Window System (gnulib_x11 dump)
   ))

;;; Use this function to use available file types
(define (octave:save-plot-to type filename)
  (define (any proc lst)
    (cond ((null? lst)      #f)
	  ((proc (car lst)) #t)
	  (else
	   (any proc (cdr lst)))))
  
  (define (draw)
    (octave:set-output-file! filename)
    (octave:replot)
    (octave:set-output! "")
    (octave:reset-terminal!)
    #t)
  
  (cond ((any (lambda (s) (string=? type s))
	      octave:supported-file-types)
	 (octave:set-terminal! type)
	 (draw))
	(else
	 (error "File type not supported in Octave (see list defined
in octave:supported-file-types for supported types)."))))

;;; Shortcuts (not really necessary, I will probably remove them)
(define (octave:save-plot-to-png filename) 
  (octave:save-plot-to "png" filename))
(define (octave:save-plot-to-postscript filename) 
  (octave:save-plot-to "postscript" filename))
(define (octave:save-plot-to-gif filename) 
  (octave:save-plot-to "gif" filename))
(define (octave:save-plot-to-jpeg filename) 
  (octave:save-plot-to "jpeg" filename))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; TODO: __pltopt__, gplot, gsplot,


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; test
;(use srfi-1); (For "iota": in Gambit use your own iota function)
;(octave:start)
;(octave:plot '(1 2 3) '(3 2 1))
;(octave:title "Foo")
;(octave:xlabel "xbar")
;(octave:ylabel "ybar")
;(octave:grid "on")
;(octave:grid "off")
;(octave:semilogx '(1 2 3) '(3 2 1))
;(octave:title "Foo2")
;(octave:semilogy '(1 2 3) '(3 2 1))
;(octave:title "Foo3")
;(octave:figure)
;(octave:loglog '(1 2 1113) '(3 3.4 2.0) '(1 1.5 211.8) '(2 2.3 21.0)  '(1 1.5 222.3) '(1.2 2.4 2222.0))
;(octave:figure)
;(octave:polar (iota 50) (iota 50))
;(octave:title "Rose")
;(octave:save-plot-to "png" "rose.png")
;(octave:figure)
;(octave:bar (iota 5) (iota 5))
;(octave:figure)
;(octave:mesh '((0 1 2 4) (1 2 2 5) (1 3 2 4) (0 2 4 6)))
;(octave:save-plot-to "ps" "foo.ps")
;(octave:errorbar (list 1.1 2.1 3.1 4.1) (list 2 3 2 3) (list .1 .2 .2 .1)  (list .1 .2 .2 .2) "'~>'")
;(octave:errorbar (list 1.1 2.1 3.1 4.1) (list 2 3 2 3) (list .1 .2 .2 .1)  (list .1 .2 .2 .2)  (list .2 .2 .2 .2)  (list .4 .4 .4 .4) "'#~>'")
;(octave:stop)

