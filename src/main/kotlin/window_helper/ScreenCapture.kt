package com.dat.cod.window_helper

import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


object ScreenCapture {

    fun capture(){
        val image: BufferedImage = Robot().createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
        ImageIO.write(image, "png", File("D:/screenshot.png"))
    }
}