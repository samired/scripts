(import '(java.io IOException)
        '(java.net Socket)
        '(java.net InetSocketAddress)
        '(java.net SocketTimeoutException)
        '(java.net UnknownHostException))
 
(if (== (count *command-line-args*) 1)
  (def hostname (first *command-line-args*))
  (
    (println "Usage: scanner &lt;hostname&gt;")
        (System/exit 1)
  ))
 
(defn port-open? [hostname port timeout]
  (let [sock-addr (InetSocketAddress. hostname port)]
    (try
     (with-open [sock (Socket.)]
       (. sock connect sock-addr timeout)
       port)
     (catch IOException e false)
     (catch SocketTimeoutException e false)
     (catch UnknownHostException e false))))
     	<li>
(defn host-port-open? [port]
  (port-open? hostname port 5000))
 
(def port-list (range 1 1024))
 
(def agents (for [port port-list] (agent port)))
 
(println (str "Scanning " hostname "..."))
 
(doseq [agent agents]
  (send-off agent host-port-open?))
 
(apply await agents)
 
(doseq [port (filter deref agents)]
       (println (str @port " is open")))
 
(shutdown-agents)