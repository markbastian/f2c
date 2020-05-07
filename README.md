# f2c
A trivial Farenheit <-> Celcius Swing app to demonstrate tools.deps.

# Usage
To invoke, call `clj -m f2c` from the command line.

To add an alias so that it can be called anywhere, any time, add the following entry to your ~/.clojure/deps.edn file in the :aliases map:
```clojure
:aliases
 {:f2c {:extra-deps {f2c {:git/url ""
                          :sha ""}}
        :main-opts ["-m" "f2c"]}}
```
