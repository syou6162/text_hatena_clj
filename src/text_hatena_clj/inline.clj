(ns text-hatena-clj.inline
  (:use [name.choi.joshua.fnparse])
  (:require [text-hatena-clj.util :as util]))

(def https? (semantics (conc (lit \h) (lit \t) (lit \t) (lit \p)
                             (opt (lit \s)))
                       #(apply str %)))

(def href
  (complex [_ util/bracket-begin
            http https?
            _ util/colon
            _ (conc util/slash util/slash)
            remainder (rep+ (except anything util/colon))
            _ util/colon
            title (rep+ (except anything util/bracket-end))
            _ util/bracket-end]
           (let [link (apply str http "://" remainder)]
             {:link link :title (apply str title)})))