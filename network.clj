(import '(java.io IOException)
        '(java.net Socket)
        '(java.net InetSocketAddress)
        '(java.net SocketTimeoutException)
        '(java.net UnknownHostException))

		
(defn host-up? [hostname timeout port]
  (let [sock-addr (InetSocketAddress. hostname port)]
    (try
     (with-open [sock (Socket.)]
       (. sock connect sock-addr timeout)
       hostname)
     (catch IOException e false)
     (catch SocketTimeoutException e false)
     (catch UnknownHostException e false))))

(defn ssh-host-up? [hostname]
      (host-up? hostname 5000 22))
	  
;(def network "192.168.0")
; scan 192.168.1.1 - 192.168.1.254  10.136.67
;(def ip-list (for [x (range 1 255)] (str network x)))
;(doseq [host (filter ssh-host-up? ip-list)]
;      (println (str host " is up")))
	   
(def network "10.136.67.")
; scan 192.168.1.1 - 192.168.1.254
(def ip-list (for [x (range 1 255)] (str network x)))
(def agents (for [ip ip-list] (agent ip)))
 
(doseq [agent agents]
  (send-off agent ssh-host-up?))
 
(apply await agents)
 
(doseq [host (filter deref agents)]
  (println (str @host " is up")))
 
(shutdown-agents)