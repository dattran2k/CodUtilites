package com.dat.cod

import ADBUtil

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    try {
        // Define the path to the adb executable

        val adbUtil = ADBUtil
        adbUtil.init()
//        adbUtil.startAdbServer()








    } catch (e: Exception) {
        println("An error occurred: ${e.message}")
    }
}