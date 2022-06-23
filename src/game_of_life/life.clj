(ns game-of-life.life)

(defn neighbors-of [[x y]]
  (for [Y (range (dec y) (+ 2 y))
        X (range (dec x) (+ 2 x))
        :when (not (= [X Y] [x y]))]
    [X Y]))

(defn count-active-neighbors [cell grid]
  (->> cell neighbors-of set (filter grid) count))

(defn update-cell [cell grid]
  (let [n (count-active-neighbors cell grid)]
    (cond (= n 3) cell
          (not= n 2) nil
          (grid cell) cell)))

(defn evolve [grid]
  (->> (mapcat neighbors-of grid)
       (map #(update-cell % grid))
       (remove nil?) set))