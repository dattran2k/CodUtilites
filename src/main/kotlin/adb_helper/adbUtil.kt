import com.dat.cod.model.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader


// ADB Utility Class
object ADBUtil {
    val adbPath = "adb/adb.exe"
    val TEST_DEVICE = "localhost:21503"

    init {

    }

    fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            // Check if adb is installed
            if (!isADBInstalled()) {
                println("adb is not installed. Please ensure the adb executable is placed in the correct path.")
                return@launch;
            }
            adbKillServer()
            adbStartServer()
            // List available devices
            val devices = listDevices()
            if (devices.isEmpty()) {
                println("No devices/emulators found.")
            }
            // Display the list of devices
            println("Available devices/emulators:")
            devices.forEachIndexed { index, device -> println("$index: $device") }
            // Choose a device to connect
//        val chosenDevice = devices[0] // For simplicity, choosing the first device. Modify as needed.
            val chosenDevice = TEST_DEVICE
            println("Connecting to device: $chosenDevice")

            // Connect to the chosen device
            val connectOutput = connectToDevice(chosenDevice)
            println(connectOutput)
        }

    }

    fun adbKillServer(): String {
        return executeCommandAdb("kill-server")
    }

    fun adbStartServer(): String {
        return executeCommandAdb("start-server")
    }

    fun isADBInstalled(): Boolean {
        val adbCheck = executeCommandAdb("version")
        return adbCheck.isNotEmpty()
    }

    fun listDevices(): List<Device> {
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

    private fun parseDevices(devicesOutput: String): List<Device> {
        val devices = mutableListOf<Device>()
        devicesOutput.lines().forEach { line ->
            if ((line.contains("device") || line.contains("emulator")) && !line.contains("List of devices attached")) {
                val parts = line.split("\t")
                devices.add(Device(parts[0], parts[1]))
            }
        }
        return devices
    }

}