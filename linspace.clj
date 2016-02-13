(defn linspace
  "A function that simulate 'linespace' function in numpy "
  [min max n]
  (let [step (/ (- max min) (dec n))] (range min (+ max step) step)))

(def x (linspace 0 2. 1000))

(view
 (xy-plot x (Math/sqrt x)))