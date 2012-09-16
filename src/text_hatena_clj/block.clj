(ns text-hatena-clj.block
  (:use [name.choi.joshua.fnparse])
  (:require [text-hatena-clj.util :as util]))

(defn tags [tags-seq]
  (->> tags-seq
       (map (fn [tags]
              (map
               (fn [[_ tag _]] (apply str tag))
               (partition 3 tags))))
       (flatten)
       (vec)))

(def header
  (complex
   [_ (rep* util/newline)
    _ (lit \*)
    num (rep+ util/digit)
    _ (lit \*)
    tags-seq (rep* (conc (lit \[)
                         (rep+ (except anything
                                       (alt util/bracket-begin
                                            util/bracket-end)))
                         (lit \])))
    title (rep+ (except anything util/newline))]
   {:num (apply str num)
    :tags (tags tags-seq)
    :title (apply str title)}))

(def header2
  (complex
   [_ (conc (lit \*) (lit \*))
    _ (rep* (lit \space))
    title (rep+ (except anything util/newline))]
   {:h2 (apply str title)}))



(def pre
  (let [greater (lit \<)
        smaller (lit \>)]
    (complex [_ (conc smaller smaller util/newline)
              pre-body (rep+ (except anything
                                     (alt greater util/newline)))
              _ (conc util/newline greater greater)]
             {:pre-body (apply str pre-body)})))

(def paragraph
  (letfn [(is-func [item] (contains? #{\* \- \< \>} item))]
    (complex [paragraph-body (rep+ (except anything (term is-func)))]
             {:paragraph (apply str paragraph-body)})))

(def block-item
  (alt header header2 paragraph pre))

(def document
  (complex [document-body (rep* block-item)
            _ emptiness]
           {:document document-body}))

(comment
  (def text "*123*[tag][tags1]Title is this!!!
Hoge is fuga.

This is it.
>>
Hogeeeee

*123*[tag][tags1]Title is this!!!
<<

*123*[tag][tags1]Second Title
booooooooooody")

  (document {:remainder text}))
