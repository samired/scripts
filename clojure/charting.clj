;You will have to include the charts4j jar in your class path. Find the jar here:
;http://charts4j.googlecode.com

;Important, you have to be connected to the Internet for this script to work
;since the images will be ultimately rendered by the Google Chart API server.

;imports
(import '(javax.swing JLabel JFrame ImageIcon))
(import '(com.googlecode.charts4j Data GCharts Plots DataUtil Color Shape Fills LineStyle AxisLabelsFactory Plot BarChartPlot Line XYLine AxisTextAlignment AxisStyle))

;a couple of Java Swing functions to display a PNG.
(defn get-image [url]
  (new ImageIcon (new java.net.URL url) "Chart"))

(defn show-image [url]
  (doto (new JFrame "Chart")
    (.add (new JLabel (get-image url)))
    (.pack)
    (.setVisible true)))

;function that uses show-image to display the chart.
(defn show-chart [chart] (show-image (. chart (toURLString))))

;function that leverages the charts4j line chart.
(defn line-chart [data]
  (. GCharts newLineChart [(. Plots newLine (. Data newData data))]))

;Very simple line chart
(def chart1 (doto (line-chart [0 90 10 100]) (.setSize 500 500)
                                          (.setTitle "Simple Chart")))

;Display the very simple line chart.
(println "Displaying chart 1")
(show-chart chart1)
(. Thread sleep 3000) ;Slide show pause

;Now for something a little more amusing.

;Defining the xy line chart function that leverages the charts4j
;xy line chart.
(defn xy-line-chart [xdata ydata]
  (. GCharts newXYLineChart [(. Plots newXYLine (. Data newData xdata) 
                                                (. Data newData ydata))]))
;trig functions
(defn sin [x] (. java.lang.Math sin x))
(defn cos [x] (. java.lang.Math cos x))

; Parametric spirograph equations.
; x(t)=(R+r)cos(t) + p*cos((R+r)t/r)
; y(t)=(R+r)sin(t) + p*sin((R+r)t/r) 

(def r 55) (def R 44) (def p -68)
;Beware not to add much more than 450 points because the Google Chart API server can't handle long URLs.
(def xseq (reduce conj [] (map (fn [t] (+ (* (+ R r) (cos (/ t 13))) (* p (cos (/ (* (+ R r) (/ t 13)) r))))) (range 450))))
(def yseq (reduce conj [] (map (fn [t] (+ (* (+ R r) (sin (/ t 13))) (* p (sin (/ (* (+ R r) (/ t 13)) r))))) (range 450))))

;Scaling the data to fit nicely in the chart
(def xseq2 (.(. DataUtil scale xseq) getData))
(def yseq2 (.(. DataUtil scale yseq) getData))

;Defining spirograph chart.
(def chart2 (doto (xy-line-chart xseq2 yseq2) (.setSize 500 500)
                                          (.setBackgroundFill (. Fills newSolidFill Color/BLACK))
                                          (.setTitle "Spirograph" Color/WHITE 14)))

;Displays spirograph.
(println "Displaying chart 2")
(show-chart chart2)
(. Thread sleep 3000) ;Slide show pause

;Now for something a little more complicated.

;S & P 500 data for the last few years. Last point is fictitious since we are still in 2008.
(def sp500 [62.960 74.560 84.300 92.200 95.890 103.800 91.600 92.270 96.870 116.930 97.540 67.160 89.770 106.880 94.750 96.280 107.840 135.330 122.300 140.340 164.860 166.260 210.680 243.370 247.840 277.080 350.680 328.710 415.140 438.820 468.660 460.920 614.120 753.850 970.840 1231.93 1464.47 1334.22 1161.02 879.390 1109.64 1213.55 1258.17 1424.71 1475.25 816.21])

;Scaling the data to fit nicely in the chart
(def sp500-seq (.(. DataUtil scaleWithinRange 0.0 1500.0 sp500) getData))

;Define a new line chart where the plot(s) is the parameter instead of the of the data.
(defn line-chart2 [plots]
  (. GCharts newLineChart plots))

;Defining basic plot. We will build upon this later.
(defn basic-plot [data] (. Plots newLine (. Data newData data)))

;S & P 500 plot
(def plot (basic-plot sp500-seq))

;S & P 500 chart
(def chart3 (doto  (line-chart2 [plot])  (.setSize 500 500)
                        (.setTitle "S & P 500|1962 - 2009")))

(println "Displaying chart 3")
(show-chart chart3)
(. Thread sleep 3000) ;Slide show pause

;That's boring. Let make things look snazzier.
(def plot (doto (basic-plot sp500-seq) 
                (.setLegend "S & P 500") ;adding legend to plot
                (.addShapeMarkers Shape/CIRCLE Color/BLACK 6) ;adding shape markers to points
                (.addShapeMarkers Shape/CIRCLE Color/WHITE 4)
                (.setColor Color/RED))) ;setting the plot color

;Redefining S & P 500 chart
(def chart4 (doto  (line-chart2 [plot])  (.setSize 500 500)
                        (.setTitle "S & P 500|1962 - 2009" Color/WHITE 14)
                        (.setAreaFill (. Fills newSolidFill Color/GRAY))
                        (.setBackgroundFill (. Fills newSolidFill Color/BLACK))
                        (.setGrid 100 10 1 1)
                        (.addXAxisLabels (. AxisLabelsFactory newNumericRangeAxisLabels 1962 2009))
                        (.addYAxisLabels (. AxisLabelsFactory newNumericRangeAxisLabels 0 1500))))

(println "Displaying chart 4")
(show-chart chart4)
(. Thread sleep 3000) ;Slide show pause

;The axis labels could be improved.
(def axis-style (. AxisStyle newAxisStyle Color/WHITE 12 AxisTextAlignment/CENTER))
(def x-axis-label (doto (. AxisLabelsFactory newNumericRangeAxisLabels 1962 2009) 
                        (.setAxisStyle axis-style)))
(def y-axis-label (doto (. AxisLabelsFactory newNumericRangeAxisLabels 0 1500)
                        (.setAxisStyle axis-style)))

;Redefining S & P 500 chart
(def chart5 (doto  (line-chart2 [plot])  (.setSize 500 500)
                        (.setTitle "S & P 500|1962 - 2009" Color/WHITE 14)
                        (.setAreaFill (. Fills newSolidFill Color/GRAY))
                        (.setBackgroundFill (. Fills newSolidFill Color/BLACK))
                        (.setGrid 100 10 1 1)
                        (.addXAxisLabels x-axis-label)
                        (.addYAxisLabels y-axis-label)))

(println "Displaying chart 5")
(show-chart chart5)
(. Thread sleep 3000) ;Slide show pause


;Defining bar chart.
(defn bar-chart [plots]
  (. GCharts newBarChart plots))

;charts4j allows you to pass the same plots to different types of charts.
(def chart6 (doto  (bar-chart [plot])  (.setSize 500 500)
                        (.setTitle "S & P 500|1962 - 2009" Color/WHITE 14)
                        (.setAreaFill (. Fills newSolidFill Color/GRAY))
                        (.setBackgroundFill (. Fills newSolidFill Color/BLACK))
                        (.setGrid 100 10 1 1)
                        (.addYAxisLabels y-axis-label)
                        (.setBarWidth 2)
                        (.setSpaceBetweenGroupsOfBars 6)))

;chart
(println "Displaying chart 6")
(show-chart chart6)
(. Thread sleep 3000) ;Slide show pause