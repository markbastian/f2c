(ns f2c.core
  (:import (javax.swing JFrame JLabel JPanel BoxLayout JTextField JComponent)
           (java.awt BorderLayout)
           (java.beans PropertyChangeListener)
           (java.awt.event ActionListener KeyListener KeyAdapter KeyEvent)))

(defonce state (atom 0.0))

(defn f2c [f]
  (* (/ (- f 32) 9) 5))

(defn wire-action [k xform ^JTextField c]
  (add-watch
    k
    state
    (fn [_ _ o n]
      (when (not= o n)
        (.setText c (str (xform n)))))))

(defn ^JTextField text-field [action]
  (doto (JTextField.)
    (.addKeyListener
      (proxy [KeyAdapter] []
        (keyReleased [^KeyEvent event]
          (let [src ^JTextField (.getSource event)]
            (try
              (let [t (Double/parseDouble (.getText src))]
                (action t))
              (catch Throwable _))))))))

(defn ^JComponent farenheit-field []
  (let [panel (JPanel.)
        box-layout (BoxLayout. panel BoxLayout/LINE_AXIS)]
    (doto panel
      (.setLayout box-layout)
      (.add (JLabel. "Farenheit"))
      (.add (text-field (fn [t] (reset! state (f2c t))))))))

(defn ^JComponent celsius-field []
  (let [panel (JPanel.)
        box-layout (BoxLayout. panel BoxLayout/LINE_AXIS)]
    (doto panel
      (.setLayout box-layout)
      (.add (JLabel. "Celsius"))
      (.add (text-field (fn [t] (reset! state t)))))))

(defn ^JComponent fields []
  (let [panel (JPanel.)
        box-layout (BoxLayout. panel BoxLayout/PAGE_AXIS)]
    (doto panel
      (.setLayout box-layout)
      (.add (farenheit-field))
      (.add (celsius-field)))))



(doto
  (JFrame. "F2C")
  (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
  (.setLayout (BorderLayout.))
  (.add (fields) BorderLayout/CENTER)
  (.doLayout)
  (.pack)
  (.setVisible true))