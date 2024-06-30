package com.dat.cod

import ADBUtil
import com.dat.cod.ui.TaskCheckerApp
import com.dat.cod.window_helper.ScreenCapture
import javafx.application.Application

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    try {
        // Define the path to the adb executable

        ADBUtil.init()
//        adbUtil.startAdbServer()
        ScreenCapture.capture()
        Application.launch(TaskCheckerApp::class.java)


    } catch (e: Exception) {
        println("An error occurred: ${e.message}")
    }
}