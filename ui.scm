(define-alias BorderLayout java.awt.BorderLayout)
(define text1  (javax.swing.JTextArea 20 20))
(define label1 (javax.swing.JLabel "Swing app in Scheme/Kawa"))
(define buttn1 (javax.swing.JButton "Click me"
                 action-listener: (lambda (evt)
                                    (label1:setText "You clicked me!"))))
(define frm1 (javax.swing.JFrame "Hello Swing in Kawa"
               default-close-operation: javax.swing.JFrame:EXIT_ON_CLOSE
               layout: (BorderLayout hgap: 5 vgap: 5)))

(frm1:add text1 BorderLayout:EAST)
(frm1:add buttn1 BorderLayout:CENTER)
(frm1:add label1 BorderLayout:SOUTH)
(frm1:pack)
(frm1:show)