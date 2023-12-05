(require '[clojure.java.io :as io]
         '[clojure.string  :as str]
         '[clojure.set     :as set])

(defn pulisci [un-array]
  (map str/trim un-array))

(defn dizionarizza [un-gratta-e-vinci-pulito]
  (let [vincenza (first un-gratta-e-vinci-pulito)
        grattanza (last un-gratta-e-vinci-pulito)
        vincenza-arrayzata (str/split vincenza #" +")
        grattanza-arrayzata (str/split grattanza #" +")]
    {:vincenza (into #{} vincenza-arrayzata) :grattanza (into #{} grattanza-arrayzata)}))

(defn numeri-dorati [dizionario-vincenza-e-grattanza]
  (set/intersection (:vincenza dizionario-vincenza-e-grattanza) (:grattanza dizionario-vincenza-e-grattanza)))

(defn due-alla-corretto [n]
  (if (= n 0) 0
      (reduce * (repeat (dec n) 2))))

(with-open [rdr (io/reader "input")]
  (let [gratta-e-vinci (map #(last (str/split % #":")) (line-seq rdr))
        gratta-e-dividi (map #(str/split % #"[|]") gratta-e-vinci)
        gratta-e-pulisci (map pulisci gratta-e-dividi)
        gratta-e-dizionarizza (map dizionarizza gratta-e-pulisci)
        win-win-win (map numeri-dorati gratta-e-dizionarizza)
        win-win-win-win (map count win-win-win)
        aaaaaa (map due-alla-corretto win-win-win-win)
        ho-vintooo (reduce + aaaaaa)]
    (prn ho-vintooo)))
