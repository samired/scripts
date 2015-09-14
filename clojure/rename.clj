(import '(java.io File))
 
(.renameTo (File. "output.txt") (File. "input.txt"))

(.renameTo (File. "docs") (File. "mydocs"))
 
(.renameTo 
    (File. (str (File/separator) "input.txt"))
    (File. (str (File/separator) "output.txt")))

(.renameTo 
    (File. (str (File/separator) "docs"))
    (File. (str (File/separator) "mydocs")))