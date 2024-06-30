package com.dat.cod.mouse_window

import java.awt.AWTException
import java.awt.Robot
import java.awt.event.InputEvent


class MouseWindow {

    fun click(x: Int, y: Int) {
        val bot: Robot = Robot()
        bot.mouseMove(x, y)
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }
}