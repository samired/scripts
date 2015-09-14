;;So we need simply to load the file and replace every , or newline character with a space character, enclose the string into [ ] and let the clojure reader do it's magic. This will return a byte vector that we can manipulate in the rest of the article to solve the problem.

(defn load-csv
  "Compute a comma separated bytes values into a clojure byte vector."
  [s]
  (read-string (str "[" (string/replace s #",|\n" " ") "]")))


(defn load-f "Load the file"
  [filepath]
  (-> filepath
      slurp
      load-csv))

(def ascii-encrypted (load-f "./resources/euler-59-cipher"))

