# f2c
A trivial Farenheit <-> Celcius Swing app to demonstrate tools.deps.

# Usage
To invoke, call `clj -m f2c` from the command line.

To add an alias so that it can be called anywhere, any time, add the following entry to your ~/.clojure/deps.edn file in the :aliases map:
```clojure
:aliases
 {;Other aliases here...
  :f2c {:extra-deps {f2c {:git/url "https://github.com/markbastian/f2c.git"
                          :sha "f5c033c540d658fb7854e5321a17a8a022ae9bcc"}}
        :main-opts ["-m" "f2c"]}}
```

Notes:

 * For public repos, the https `:git/url` key is fine.
 * For private repos, you must register an ssh key with the server and use the ssh clone url (e.g. _git@github.com:markbastian/f2c.git_)
 * The sha can be for any commit and will use that commit's code. If you want the latest and greatest, use that sha.

Once this alias is registered, invoke with `clj -A:f2c`. It's that easy!

Note that I called the alias `:f2c`, but you can use anything you want.