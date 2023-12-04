(require '[clojure.java.io :as io])

(defn file-to-array [nome-file]
  (with-open [rdr (io/reader nome-file)]
    (into-array (line-seq rdr))))

(defn affianca-numeri [linea]
  (let [numeri-in-linea (filter #(Character/isDigit %) linea)
        primo-numero (Character/getNumericValue (first numeri-in-linea))
        ultimo-numero (Character/getNumericValue (last numeri-in-linea))
        numero-linea (str primo-numero ultimo-numero)]
    (Integer/parseInt numero-linea)))

(let [mappina (map affianca-numeri (file-to-array "input"))
      sommina (reduce + mappina)
      ]
  (println "mappina:" mappina)
  (println "sommina:" sommina)
  )
