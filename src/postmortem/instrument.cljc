(ns postmortem.instrument
  (:require #?@(:clj [[net.cgrand.macrovich :as macros]
                      [postmortem.instrument.clj :as clj]])
            ;; necessary to ensure for CLJS that ns is loaded at runtime
            [postmortem.instrument.core :as instr])
  #?(:cljs (:require-macros [net.cgrand.macrovich :as macros]
                            postmortem.instrument.cljs
                            [postmortem.instrument :refer [instrument unstrument]])))

(macros/deftime

  (defmacro instrument
    "Instruments the vars named by sym-or-syms, a symbol or collection of symbols.
  If a symbol identifies a namespace then all symbols in that namespace will be
  enumerated. Returns a collection of syms naming the vars instrumented."
    ([sym-or-syms]
     (macros/case :clj `(clj/instrument ~sym-or-syms)
                  :cljs `(postmortem.instrument.cljs/instrument ~sym-or-syms)))
    ([sym-or-syms opts]
     (macros/case :clj `(clj/instrument ~sym-or-syms ~opts)
                  :cljs `(postmortem.instrument.cljs/instrument ~sym-or-syms ~opts))))

  (defmacro unstrument
    "Undoes instrument on the vars named by sym-or-syms, specified as in instrument.
  With no args, unstruments all instrumented vars.
  Returns a collection of syms naming the vars unstrumented."
    ([]
     (macros/case :clj `(clj/unstrument)
                  :cljs `(postmortem.instrument.cljs/unstrument)))
    ([sym-or-syms]
     (macros/case :clj `(clj/unstrument ~sym-or-syms)
                  :cljs `(postmortem.instrument.cljs/unstrument ~sym-or-syms))))

  )