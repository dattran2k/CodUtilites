package com.dat.cod.mouse_window

import java.awt.MouseInfo
import java.awt.PointerInfo
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class GlobalMouseListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        val pointerInfo: PointerInfo = MouseInfo.getPointerInfo()
        val location = pointerInfo.location
        println("Mouse clicked at: (${location.x}, ${location.y})")
    }
}