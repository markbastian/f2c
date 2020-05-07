(ns f2c.core
  (:import (javax.swing JFrame JLabel JTextField UIManager)
           (java.awt GridLayout Dimension)
           (java.awt.event KeyAdapter KeyEvent FocusListener)))

(defn farenheit->celsius [f]
  (double (/ (* (- f 32) 5) 9)))

(defn celsius->farenheit [c]
  (double (+ (/ (* 9 c) 5) 32)))

(defn wire-action [ref watch-key xform ^JTextField text-field]
  (add-watch
    ref
    watch-key
    (fn [_ _ o n]
      (when (not= o n)
        (.setText text-field (str (xform n)))))))

(defn action-fn [event xform]
  (let [src ^JTextField (.getSource event)]
    (try
      (let [t (Double/parseDouble (.getText src))]
        (xform t))
      (catch Throwable _))))

(defn ^JTextField text-field [xform]
  (doto (JTextField.)
    (.setMinimumSize (Dimension. 300 32))
    (.setPreferredSize (Dimension. 300 32))
    (.setHorizontalAlignment JTextField/RIGHT)
    (.addFocusListener
      (reify FocusListener
        (focusGained [_ _])
        (focusLost [_ event] (action-fn event xform))))
    (.addKeyListener
      (proxy [KeyAdapter] []
        (keyPressed [^KeyEvent event]
          (let [key-code (.getKeyCode event)]
            (condp = key-code
              KeyEvent/VK_ENTER (action-fn event xform)
              nil)))))))

(defn launch-app
  ([state]
   (UIManager/setLookAndFeel
     (UIManager/getSystemLookAndFeelClassName))
   (let [c (text-field (fn [t] (reset! state t)))
         f (text-field (fn [t] (reset! state (farenheit->celsius t))))]
     (wire-action state :farenheit celsius->farenheit f)
     (wire-action state :celsius identity c)
     (doto
       (JFrame. "F2C")
       (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
       (.setLayout (GridLayout. 2 2))
       (.add (JLabel. "Celsius"))
       (.add c)
       (.add (JLabel. "Farenheit"))
       (.add f)
       (.doLayout)
       (.pack)
       (.setVisible true))))
  ([] (launch-app (atom 100))))