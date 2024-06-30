import java.io.BufferedReader
import java.io.InputStreamReader


// ADB Utility Class
object ADBUtil {
    val adbPath = "adb/adb.exe"

    init {

    }
    fun init(){

        // Check if adb is installed
        if (!isADBInstalled()) {
            println("adb is not installed. Please ensure the adb executable is placed in the correct path.")
            return;
        }
        adbKillServer()
        // List available devices
        val devices = listDevices()
        if (devices.isEmpty()) {
            println("No devices/emulators found.")
            return
        }
        // Display the list of devices
        println("Available devices/emulators:")
        devices.forEachIndexed { index, device -> println("$index: $device") }
        // Choose a device to connect
        val chosenDevice = devices[0] // For simplicity, choosing the first device. Modify as needed.
        println("Connecting to device: $chosenDevice")

        // Connect to the chosen device
        val connectOutput = connectToDevice(chosenDevice)
        println(connectOutput)
    }
    fun adbKillServer(): String {
        return executeCommandAdb("kill-server")
    }

    fun startAdbServer(): String {
        return executeCommandAdb("start-server")
    }

    fun isADBInstalled(): Boolean {
        val adbCheck = executeCommandAdb("version")
        return adbCheck.isNotEmpty()
    }

    fun listDevices(): List<String> {
        val devicesOutput = executeCommandAdb("devices")
        return parseDevices(devicesOutput)
    }

    fun connectToDevice(deviceId: String): String {
        return executeCommandAdb("connect $deviceId")
    }

    private fun executeCommandAdb(command: String): String {
        return executeCommand("$adbPath $command")
    }

    private fun executeCommand(command: String): String {
        println("executeCommand $command")
        val process = Runtime.getRuntime().exec(command)
        val output = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
        }
        println("executeCommand output : $output")
        return output.toString()
    }

    private fun parseDevices(devicesOutput: String): List<String> {
        val devices = mutableListOf<String>()
        devicesOutput.lines().forEach { line ->
            if ((line.contains("device") || line.contains("emulator")) && !line.contains("List of devices attached")) {
                devices.add(line.split("\t")[0])
            }
        }
        return devices
    }
}