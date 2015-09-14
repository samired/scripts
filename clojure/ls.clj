(use '[clojure.java.io :only [file]])

 
(->> (file ".") .listFiles (filter #(.isDirectory %)) (map str))
 
(->> (file ".") .listFiles (filter #(.isFile %)) (map str))
 
(->> (file ".") .listFiles (filter #(.isHidden %)) (map str))	
