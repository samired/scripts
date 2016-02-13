;; by Anthony Simpson, http://www.acidrayne.net
;; March 24, 2009
;;
;; Copyright (c) Anthony Simpson, 2009. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.
;;
;; File: configfile.clj
;;
;; This library can be used to read simple configuration files which
;; have a key = value format into a map and then used in the program.
;; You can parse the map back to the file when finished, doing so
;; will reflect any changes you have made to the map back to the file.
;; You can add or remove key = value pairs.

(ns net.acidrayne.configfile
  (:use [clojure.contrib.duck-streams :only (spit read-lines)]))

;===============================================================================
; File Reader
;===============================================================================

(defn- read-file
  "Reads file n into a lazy sequence of lines from the file"
  [f]
  (read-lines f))

(defn- remove=
  "Combines all elements of the sequence into a single string and removes all instances
   of '='"
  [f]
  (map (comp #(apply str %) (fn [s] (remove #(= \= %) s))) (read-file f)))

  (defn- split-spaces
  "Trims all double spaces out of a string and replaces them with single spaces."
  [s]
  (.split (.replaceAll s "  " " ") " "))

(defn- format-file
  "Returns a string containing whats inside the file minus the line separators
   and then splits it at every space."
  [f]
  (split-spaces (apply str (interpose " " (remove= f)))))

(defn map-config
  "Reads a configuration text file into a map of key-value pairs."
  [f]
  (apply hash-map (format-file f)))

;===============================================================================
; File Parser
;===============================================================================

(defn- make-file-seq
  "Turns a map into a vector and put's a = between each key and value and places
   a line separator at the end of each line."
  [m]
  (for [[k v] m] (conj [] k " = " v (System/getProperty "line.separator"))))

(defn format-out-file
  "Concatenates it's input seq and then turns it into a single string."
  [stringify file]
  (spit file (apply str (apply concat (make-file-seq stringify)))))

(comment

;; Example of use.
;; Assume file text.txt contains the following:
;;
;; foo = bar
;; haha = hehe
;;
;; After use file will contain the following:
;;
;; foo = bar
;; haha = hehe
;; oh = noes

(defn -main []
  (let [x (ref (map-config "text.txt"))]
    (println @x)
    (println (@x "haha"))
    (dosync
      (ref-set x (assoc @x "oh" "noes"))
    (println @x)
    (format-out-file @x "text.txt"))))
)
