(ns text-hatena-clj.block-test
  (:use clojure.test
        text-hatena-clj.block))

(deftest a-test
  (testing "header test"
    (is (= (header {:remainder "*123*[tag1][tag2]Title
Content is here."})
           [{:num "123", :tags ["tag1" "tag2"], :title "Title"}
            {:remainder '(\newline \C \o \n \t \e \n \t \space
                          \i \s \space \h \e \r \e \.)}])))
  
  (testing "header2 test"
    (is (= (header2 {:remainder "** Subtitle"})
           [{:h2 "Subtitle"} {:remainder nil}]))
    (is (= (header2 {:remainder "**Subtitle"})
           [{:h2 "Subtitle"} {:remainder nil}])))
  
  (testing "pre test"
        (is (= (pre {:remainder ">>
pre body!!
<<"})
               [{:pre-body "pre body!!"} {:remainder nil}])))

  (testing "paragraph test"
    (is (= (paragraph {:remainder "test\ndesu"})
           [{:paragraph "test\ndesu"} {:remainder nil}]))
    (is (= (paragraph {:remainder "test\ndesu\n*aaa"})
           [{:paragraph "test\ndesu\n"} {:remainder '(\* \a \a \a)}])))

  (testing "block-item test"
    (is (= (block-item {:remainder "** test
This is a pen."})
           [{:h2 "test"}
            {:remainder '(\newline \T \h \i \s \space
                          \i \s \space \a \space \p \e \n \.)}]))

    (is (= (block-item {:remainder ">>
This is a pen.
<<

That is another pen.
"})
           [{:pre-body "This is a pen."}
            {:remainder '(\newline \newline \T \h \a \t \space
                          \i \s \space \a \n \o \t \h \e \r \space
                          \p \e \n \. \newline)}]))))