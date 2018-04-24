package io.crdant.hello

import Electron.BrowserWindow
import Electron.BrowserWindowConstructorOptions
import Electron.App

external fun require(module:String):dynamic
external val process : dynamic = definedExternally

class HelloElectron () {
  val app : App = require("electron").app
  var closed = true

  fun createWindow() {
    val window = BrowserWindow(js("{width: 800, height: 600}"))
    window.loadURL("file://" + app.getAppPath() + "/index.html")

    closed = false

    with(window) {
      on ("close") {
        closed = true
      }
    }

  }

  fun run() {
    println ()
    with(app) {
      setName("Hello Electron")

      on("ready", ::createWindow)

      on("window-all-closed") {
        if ( process.platform != "darwin") {
          app.quit()
        }
      }

      on("activate") {
        if ( closed ) createWindow()
      }
    }
  }

}

fun main(args: Array<String>) {
  val application = HelloElectron()
  application.run()
}
