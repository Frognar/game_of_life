(ns game-of-life.life-test
  (:require [clojure.test :refer :all]
            [game-of-life.life :refer :all]))

(def cell [0 0])
(defn take-neighbors [n] (->> cell neighbors-of (take n) set))

(deftest neighbors-of-test
  (testing "has awareness of the neighbors of a cell"
    (is (= #{[-1 -1] [0 -1] [1 -1]
             [-1 0] #_cell [1 0]
             [-1 1] [0 1] [1 1]} (take-neighbors 8)))))

(deftest count-active-neighbors-test
  (testing "has awareness of the active neighbors of a cell"
    (is (= 0 (count-active-neighbors cell (conj (take-neighbors 0) [42 43]))))
    (is (= 1 (count-active-neighbors cell (conj (take-neighbors 1) [42 43]))))
    (is (= 2 (count-active-neighbors cell (conj (take-neighbors 2) [42 43]))))
    (is (= 3 (count-active-neighbors cell (conj (take-neighbors 3) [42 43]))))
    (is (= 4 (count-active-neighbors cell (conj (take-neighbors 4) [42 43]))))
    (is (= 5 (count-active-neighbors cell (conj (take-neighbors 5) [42 43]))))
    (is (= 6 (count-active-neighbors cell (conj (take-neighbors 6) [42 43]))))
    (is (= 7 (count-active-neighbors cell (conj (take-neighbors 7) [42 43]))))
    (is (= 8 (count-active-neighbors cell (conj (take-neighbors 8) [42 43]))))
    ))

(deftest update-cell-test
  (testing "Any live cell with fewer than two live neighbours dies, as if by underpopulation."
    (is (= nil (update-cell cell (conj (take-neighbors 0) cell))))
    (is (= nil (update-cell cell (conj (take-neighbors 1) cell)))))
  (testing "Any live cell with two or three live neighbours lives on to the next generation"
    (is (= cell (update-cell cell (conj (take-neighbors 2) cell))))
    (is (= cell (update-cell cell (conj (take-neighbors 3) cell)))))
  (testing "Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction."
    (is (= cell (update-cell cell (take-neighbors 3)))))
  (testing "Any live cell with more than three live neighbours dies, as if by overpopulation."
    (is (= nil (update-cell cell (conj (take-neighbors 4) cell))))
    (is (= nil (update-cell cell (conj (take-neighbors 5) cell))))
    (is (= nil (update-cell cell (conj (take-neighbors 6) cell))))
    (is (= nil (update-cell cell (conj (take-neighbors 7) cell))))
    (is (= nil (update-cell cell (conj (take-neighbors 8) cell)))))
  (testing "Ignore inactive cells with not three live neighbours"
    (is (= nil (update-cell cell (take-neighbors 0))))
    (is (= nil (update-cell cell (take-neighbors 1))))
    (is (= nil (update-cell cell (take-neighbors 2))))
    (is (= nil (update-cell cell (take-neighbors 4))))
    (is (= nil (update-cell cell (take-neighbors 5))))
    (is (= nil (update-cell cell (take-neighbors 6))))
    (is (= nil (update-cell cell (take-neighbors 7))))
    (is (= nil (update-cell cell (take-neighbors 8)))))
  )

(deftest evolve-test
  (testing "evolves an entire grid of cells"
    (let [oscillator [#{[0 1] [1 1] [2 1]}
                     #{[1 0] [1 1] [1 2]}]]
      (is (= (second oscillator)
             (evolve (first oscillator)))))))
