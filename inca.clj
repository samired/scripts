(require '[incanter.core :as in]
         '[incanter.stats :as st]
         '[incanter.charts :as ch])

(defn generate-sample-cluster [x y sd n]
  (let [generate-by #(st/sample-normal n :mean (+ % (rand 2)) :sd sd)]
    (map vector (generate-by x) (generate-by y))))

(defn plot-clusters-with-function [f [fcluster & rclusters :as clusters]]
  (let [init-plot (ch/scatter-plot (map first fcluster) (map second fcluster))
        all-x-elements (map first (apply concat clusters))
        x1 (apply min all-x-elements)
        x2 (apply max all-x-elements)]
    (reduce #(ch/add-points %1 (map first %2) (map second %2))
            (ch/add-function init-plot f x1 x2)
            rclusters)))

(defn merge-clusters-with-label [[cluster-a cluster-b]]
  (concat (map #(vector -1 %) cluster-a)
          (map #(vector 1 %) cluster-b)))

(defn inner-product [xs ys]
  (reduce + (map * xs ys)))

(defn norm [xs]
  (in/sqrt (reduce + (map #(in/pow % 2) xs))))

(defn loop-to-fix [label+xss]
  (let [limit 10000 ;; limit of loop (when faild to learn)
        roh 0.2 ;; learning coefficient
        r (apply max (map (comp norm second) label+xss))] ;; R = max_i (|[xs_i]|)
    (letfn [(modify [[modified? ws bias] [label xs]]
              (if (< (* label (+ (inner-product ws xs) bias)) 0)
                [true ;; update
                 (map #(+ %1 (* roh label %2)) ws xs)
                 (+ bias (* roh label r))]
                [modified? ws bias]))] ;; nothing wrong
      (loop [ws [0 0] bias 1 i 0]
        (let [[modified? ws bias] (reduce modify [false ws bias] label+xss)]
          (if (or (not modified?) (< limit i))
            [ws bias]
            (recur ws bias (inc i))))))))

;; ws = [a b], xs = (x, y) (or = (x_1, x_2))
;; g(xs) = (ws . xs) + bias = ax + by + bias = 0
;; y = - (ax + bias) / b
(defn display-result [[[a b] bias] two-clusters]
  (plot-clusters-with-function
   (fn [x] (- (/ (+ (* a x) bias) b)))
   two-clusters))

(defn sperceptron [two-clusters]
  (let [label+xss (merge-clusters-with-label two-clusters)
        ws+bias (loop-to-fix label+xss)]
    (in/view (display-result ws+bias two-clusters))
    ws+bias))

(sperceptron [(generate-sample-cluster 4 2 2 100)
              (generate-sample-cluster -3 10 2 100)])