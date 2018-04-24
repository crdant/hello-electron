package io.crdant.hello

import Electron.BrowserWindow
import Electron.BrowserWindowConstructorOptions
import Electron.AboutPanelOptionsOptions
import Electron.App

external fun require(module:String):dynamic
external val process : dynamic = definedExternally

class HelloElectron () {
  val app : App = require("electron").app
  var closed = true

  fun createWindow() {
    val window = BrowserWindow(
        object : BrowserWindowConstructorOptions {
          override var width : Number? = 800
          override var height : Number? = 600
      })
    window.setTitle("Hello World")
    window.loadURL("file://" + app.getAppPath() + "/index.html")

    closed = false

    with(window) {
      on ("close") {
        closed = true
      }
    }

  }

  fun run() {
    with(app) {
      setName("Hello Electron")
      setAboutPanelOptions( object : AboutPanelOptionsOptions {
        override var applicationName : String? = "Hello Electron"
        override var copyright : String? = "© 2018 Pivotal Software, released under the Apache License version 2.0"
        override var credits : String? = "Icon 'Hello' by Jonathan Collie from https://thenounproject.com"
      })

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
