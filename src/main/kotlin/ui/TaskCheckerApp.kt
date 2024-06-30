package com.dat.cod.ui

import ADBUtil
import com.dat.cod.model.Device
import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader


class TaskCheckerApp : Application() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val devices: ObservableList<Device> = FXCollections.observableArrayList()

    override fun start(primaryStage: Stage) {
        val tasksToCheck = listOf("BlueStacks.exe", "adb.exe")

        val tableView = TableView<Device>().apply {
            columns.addAll(
                TableColumn<Device, String>("Device ID").apply {
                    cellValueFactory = PropertyValueFactory("id")
                },
                TableColumn<Device, String>("Status").apply {
                    cellValueFactory = PropertyValueFactory("status")
                }
            )
            items = devices
        }

        val checkButton = Button("Check Devices").apply {
            setOnAction {
                scope.launch {
                    val runningDevices = withContext(Dispatchers.IO) { ADBUtil.listDevices() }
                    devices.setAll(runningDevices)
                }
            }
        }

        val layout = VBox(10.0, Label("Device Checker"), checkButton, tableView)
        val scene = Scene(layout, 400.0, 300.0)

        primaryStage.apply {
            title = "Device Checker"
            this.scene = scene
            setOnCloseRequest {
                println("Application is closing...")
                scope.cancel() // Cancel all coroutines when the application is closed
                Platform.exit()
            }
            show()
        }
    }

}

fun main() {
    Application.launch(TaskCheckerApp::class.java)
}

