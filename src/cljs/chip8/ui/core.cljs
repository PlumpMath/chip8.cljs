(ns chip8.ui.core
  (:require [chip8.cpu :as cpu]
            [chip8.ui.screen :as screen]
            [chip8.ui.sound :as sound]
            [chip8.ui.rom :as rom]
            [goog.dom :as dom]
            [goog.events :as events]
            [goog.net.EventType :as event-type]))

;; NOTE:
;; This app dosen't us om or reagent since I want to make the tutorial more simple.
;; That's why we still use dom here.

;; The full application state
(def app-state (cpu/make-cpu))


;; # Load rom event
;;
;; This function define the chip8 cpu action when user
;; select rom to play. It will also passed the rom data
;; to cpu in js/Unit8Array format.
(defn load-rom
  [name states]
  (let [req (goog.net.XhrIo.)]
    (.setResponseType req "arraybuffer")
    (events/listen req event-type/SUCCESS
                   (fn [n]
                     ;;(cpu/load-rom states (js/Uint8Array. (.getResponse req)))

                     ;; log data
                     ;; (.log js/console  ">>>> "  (js/Uint8Array. (.getResponse req)))
                     ))

    (.send req (str "roms/" name) "GET")))

(defn main []

  ;; Initial Rom Selector
  (rom/update-rom-selector app-state)

  ;; Initial cpu state
  ;;  (cpu/initial-vm app-state)

  (screen/initial app-state)

  ;; Track when user select another rom
  (rom/on-select-event
   (fn [rom-name]
     ;; Display rom name for debug
     (.log js/console "Select rom: " rom-name)

     ;;(load-rom rom-name @app-state)

     ;; Blur rom-selector
     ;; FIXME:
     ;;(.blur (dom/getElement (:id (:rom @app-state))))

     ;; Make focus on canvas
     ;;        (.focus (screen/get-screen-canvas app-state))
     )))