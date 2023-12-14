(defproject advent-of-code-2023 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0",
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/test.check "1.1.0"]]
  :main ^:skip-aot advent-of-code-2023.core
  :target-path "target/%s"
  :plugins [[dev.weavejester/lein-cljfmt "0.11.2"]
            [lein-zprint "1.2.8"]
            [lein-exec "0.3.7"]]
  :profiles {:uberjar {:aot :all,
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
