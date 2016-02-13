(import '(java.awt AWTException Robot Rectangle Toolkit)
        '(java.awt.image BufferedImage)
        '(java.io File IOException)
        '(javax.imageio ImageIO))
(defn screen-grab [file-name]
"Capture screen shot"
  (let [img-type (second (re-find (re-matcher #"\.(\w+)$" file-name)))
        capture (.createScreenCapture (Robot.)
                                      (Rectangle. (.getScreenSize (Toolkit/getDefaultToolkit))))
        file (File. file-name)]
    (ImageIO/write capture img-type file)))

(screen-grab "test.png")

;;------------------------------

(load-file "C:/test.clj")

(ns choosen-name-space) "for making name spaces"

;;------------------------------
;;"Fetching Document"

(slurp "some.txt")
;;------------------------------
(with-open [rdr (java.io.BufferedReader. 
                  (java.io.FileReader. "project.clj"))]
   (let [seq (line-seq rdr)]
     (count seq)))
;;------------------------------
(defn fetch-url[address]
   (with-open [stream (.openStream (java.net.URL. address))]
     (let  [buf (java.io.BufferedReader. 
                 (java.io.InputStreamReader. stream))]
       (apply str (line-seq buf)))))

 (fetch-url "http://google.com")
;;--------------------------------
(defn fetch-data [url]
   (let  [con    (-> url java.net.URL. .openConnection)
          fields (reduce (fn [h v] 
                           (assoc h (.getKey v) (into [] (.getValue v))))
                         {} (.getHeaderFields con))
          size   (first (fields "Content-Length"))
          in     (java.io.BufferedInputStream. (.getInputStream con))
          out    (java.io.BufferedOutputStream. 
                  (java.io.FileOutputStream. "out.file"))
          buffer (make-array Byte/TYPE 1024)]
     (loop [g (.read in buffer)
            r 0]
       (if-not (= g -1)
         (do
           (println r "/" size)
           (.write out buffer 0 g)
           (recur (.read in buffer) (+ r g)))))
     (.close in)
     (.close out)
     (.disconnect con)))

 (fetch-data "http://google.com")
;;------------------------------
(defn socket [host port]
   (let [socket (java.net.Socket. host port)
         in (java.io.BufferedReader. 
             (java.io.InputStreamReader. (.getInputStream socket)))
         out (java.io.PrintWriter. (.getOutputStream socket))]
     {:in in :out out}))

 (def conn (socket "irc.freenode.net" 6667))
 (println (.readLine (:in conn)))