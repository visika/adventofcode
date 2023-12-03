(use 'clojure.java.io)
(require ['clojure.string :as str])

(defn mappatura [trovanza]
  (if (nil? trovanza) 0 (Integer. (first (str/split trovanza #" ")))))

(defn analizza-mostranza [mostranza]
  (let [find-red   (re-find #"\d+ red"   mostranza)
        find-green (re-find #"\d+ green" mostranza)
        find-blue  (re-find #"\d+ blue"  mostranza)]
    {:red   (mappatura find-red)
     :green (mappatura find-green)
     :blue  (mappatura find-blue)}))

(defn approva-mostranza [dizionario]
  (let [max-red   12
        max-green 13
        max-blue  14]
    (and (<= (:red   dizionario) max-red)
         (<= (:green dizionario) max-green)
         (<= (:blue  dizionario) max-blue))))

(defn numeretto [line]
  (let [linea-divisa (str/split line #":")
        id (Integer. (last (str/split (first linea-divisa) #" ")))
        mostranze (str/split (last linea-divisa) #";")
        analizzate (map analizza-mostranza mostranze)
        approvate (map approva-mostranza analizzate)]
    (if (every? identity approvate) id 0)))

;; (with-open [rdr (reader "input")]
;;   (prn (reduce + (map numeretto (line-seq rdr)))))

(defn power [set-of-cubes]
  (* (:red   set-of-cubes)
     (:green set-of-cubes)
     (:blue  set-of-cubes)))

(defn minimal-cubes [mostranze-analizzate]
  (let [reds   (map :red   mostranze-analizzate)
        greens (map :green mostranze-analizzate)
        blues  (map :blue  mostranze-analizzate)]
    {:red   (apply max reds)
     :green (apply max greens)
     :blue  (apply max blues)}))

(defn minimalismo-lineare [line]
  (let [linea-divisa (str/split line #":")
        id (Integer. (last (str/split (first linea-divisa) #" ")))
        mostranze (str/split (last linea-divisa) #";")
        analizzate (map analizza-mostranza mostranze)
        minimali (minimal-cubes analizzate)]
    minimali))

(with-open [rdr (reader "input")]
  (prn (reduce + (map (comp power minimalismo-lineare) (line-seq rdr)))))
