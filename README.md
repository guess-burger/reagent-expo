# Edit

I've modified this to be a simple example of a re-frame based project
which makes use of [NativeBase](https://nativebase.io/).

This shows a working example of how to load custom fonts into Expo
which is require in order for NativeBase.

# Original README

```
$ yarn
$ shadow-cljs watch app
;; wait for first compile to finish or expo gets confused
$ yarn start

;; production build
$ shadow-cljs release app
;; no idea what to do for expo
```

## Notes

The `:app` build will create an `app/index.js`. In `release` mode that is the only file needed. In dev mode the `app` directory will contain many more `.js` files.

`:init-fn` is called after all files are loaded and in the case of `expo` must render something synchronously as it will otherwise complain about a missing root component. The `shadow.expo/render-root` takes care of registration and setup.

You should disable the `expo` live reload stuff and let `shadow-cljs` handle that instead as they will otherwise interfere with each other.

Source maps don't seem to work properly. `metro` propably doesn't read input source maps when converting sources as things are correctly mapped to the source .js files but not their sources.

Initial load in dev is quite slow since `metro` processes the generated `.js` files.

`reagent.core` loads `reagent.dom` which will load `react-dom` which we don't have or need. Including the `src/main/reagent/dom.cljs` to create an empty shell. Copied from [re-natal](https://github.com/drapanjanas/re-natal/blob/master/resources/cljs-reagent6/reagent_dom.cljs).


## Gavin Notes

### General stuff

Getting cursive to work with this was a headache

This helped: https://andrearichiardi.com/blog/posts/clojurescript-cursive-shadow-setup.html

However, it seems like deps.edn support was finished but I can't get the repl to work without
tricking it into thinking it's a lein project.

I can get cursive to create a deps.edn based project but even then repls don't work!

### REPL

```clojure
(shadow/repl :app)
```

This doesn't ACTUALLY WORK though. it just times out on functions :(