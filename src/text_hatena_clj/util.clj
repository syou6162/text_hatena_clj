(ns text-hatena-clj.util
  (:use [name.choi.joshua.fnparse])
  (:refer-clojure :exclude [newline]))

(def digit (term #(Character/isDigit %)))
(def bracket-begin (lit \[))
(def bracket-end (lit \]))
(def newline (lit \newline))
(def colon (lit \:))
(def slash (lit \/))
