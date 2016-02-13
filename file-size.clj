(use '[clojure.java.io :only [file]])

(defn file-size [path]
  (let [f (file path)]
    (if (.isFile f) (.length f)
      (reduce + (map file-size (.listFiles f))))))

(file-size "/")