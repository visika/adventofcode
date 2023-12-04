(require '[clojure.java.io :as io])

(defn file-to-array [nome-file]
  (with-open [rdr (io/reader nome-file)]
    (into-array (line-seq rdr))))

(defn affianca-numeri [sequenza]
  (let [affiancamento (str (first sequenza) (last sequenza))]
    (Integer/parseInt affiancamento)))

(defn scansiona-linea [linea]
  (map last (re-seq #"(?=(one|two|three|four|five|six|seven|eight|nine|\d))" linea)))

(defn numerifica [sequenza-scansionata]
  (let [mappatura {"one" 1
                   "two" 2
                   "three" 3
                   "four" 4
                   "five" 5
                   "six" 6
                   "seven" 7
                   "eight" 8
                   "nine" 9
                   "1" 1
                   "2" 2
                   "3" 3
                   "4" 4
                   "5" 5
                   "6" 6
                   "7" 7
                   "8" 8
                   "9" 9}]
    (map mappatura sequenza-scansionata)))

;; (let [mappina (map affianca-numeri (file-to-array "input"))
;;       sommina (reduce + mappina)
;;       ]
;;   (println "mappina:" mappina)
;;   (println "sommina:" sommina)
;;   )

;; (println (map numerifica (scansiona-linea (first (file-to-array "input")))))

(let [scansionate (map scansiona-linea (file-to-array "input"))
      numerizzate (map numerifica scansionate)
      affiancate (map affianca-numeri numerizzate)]
  (println (apply + affiancate)))
