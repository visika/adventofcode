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

;; La logica qui diventa complicata.
;; Ho bisogno di tenere in considerazione lo stato del sistema ed estrarre le informazioni rilevanti.
;; Ragionando, arrivo alla conclusione che per ottenere la somma delle carte
;; posso considerare la quantità della prima carta come fissata,
;; perché questa farà aumentare il numero solo delle carte successive.
;; Posso segnare questo valore e scartare per sempre la prima carta.
;; Si avvia un processo iterativo, in cui ho una variabile che accumula valori
;; e una funzione che calcola il valore sulla prima carta successiva,
;; lo conserva nella variabile accumulatrice e cambia lo stato delle carte successive.
;;
;; Le quantità che ritengo utili sono:
;; card_id: solo a scopo di chiarezza
;; numero_carte: quante carte ci sono con quel card_id particolare
;; score_carta: il punteggio che mi dice quanti card_id dopo quella attuale sono interessati da modifica
;; quidi score_carta mi dice quante carte successive coinvolgere;
;; numero_carte mi dice di quanto devo aumentare le carte successive.

(defn costruisci-card
  "Una carta è un dizionario con :score e :accumulate."
  [linea]
  (let [gratta-e-vinci (last (str/split linea #":"))
        id (first (str/split linea #":"))
        gratta-e-dividi (str/split gratta-e-vinci #"[|]")
        gratta-e-pulisci (pulisci gratta-e-dividi)
        gratta-e-dizionarizza (dizionarizza gratta-e-pulisci)
        doratura (numeri-dorati gratta-e-dizionarizza)
        score (count doratura)]
    {:id id :score score :accumulate 1}))

(defn propaga-carte
  "Uno stato è definito come una lista ordinata di carte."
  [stato]
  (let [score-carta-attuale (:score (first stato))
        accumulate-carta-attuale (:accumulate (first stato))
        stato-successivo (rest stato)
        conteggio (count stato-successivo)
        carte-da-aumentare (take score-carta-attuale stato-successivo)
        carte-aumentate (map #(assoc % :accumulate (+ (:accumulate %) accumulate-carta-attuale)) carte-da-aumentare)
        resto-delle-carte (take-last (- conteggio score-carta-attuale) stato-successivo)]
    (concat carte-aumentate resto-delle-carte)))

(with-open [rdr (io/reader "test")]
  (let [stato-iniziale (map costruisci-card (line-seq rdr))
        propagazione-1 (propaga-carte stato-iniziale)
        propagazione-2 (propaga-carte propagazione-1)
        propagazione-3 (propaga-carte propagazione-2)]
    (prn "Score stato iniziale")
    (prn (:score (first stato-iniziale)))
    (prn stato-iniziale)
    (prn propagazione-1)
    (prn propagazione-2)
    (prn propagazione-3)
    (prn 1)))

(empty? {}) ;; => true

(defn somma-carte
  ([stato]
   (somma-carte stato 0))
  ([stato totale-accumulato]
   (if (empty? stato)
     totale-accumulato
     (recur (propaga-carte stato) (+ (:accumulate (first stato)) totale-accumulato)))))

(with-open [rdr (io/reader "test")]
  (let [stato-iniziale (map costruisci-card (line-seq rdr))]
    (prn (somma-carte stato-iniziale))))

(with-open [rdr (io/reader "input")]
  (let [stato-iniziale (map costruisci-card (line-seq rdr))]
    (prn (somma-carte stato-iniziale))))
