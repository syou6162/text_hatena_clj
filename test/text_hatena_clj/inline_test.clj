(ns text-hatena-clj.inline-test
  (:use clojure.test
        text-hatena-clj.inline))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= (https? {:remainder "https://"})
           ["https" {:remainder '(\: \/ \/)}]))
    (is (= (https? {:remainder "http://"})
           ["http" {:remainder '(\: \/ \/)}]))
    
    (is (= (href {:remainder
                  "[http://yasuhisay.info:Page of yasuhisa]"})
           [{:link "http://yasuhisay.info",
             :title "Page of yasuhisa"} {:remainder nil}]))))