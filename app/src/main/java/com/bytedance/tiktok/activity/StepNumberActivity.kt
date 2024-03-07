package com.bytedance.tiktok.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.bytedance.tiktok.databinding.ActivityStepNumberBinding
import com.bytedance.tiktok.utils.GroundTruthDevice
import com.lib.base.ui.BaseBindingActivity
import uk.ac.ox.eng.stepcounter.StepCounter
import java.io.FileWriter
import java.io.IOException

class StepNumberActivity: BaseBindingActivity<ActivityStepNumberBinding>({ ActivityStepNumberBinding.inflate(it)}) {
    // sampling frequency in Hz
    private val SAMPLING_FREQUENCY = 100

    // request code for permissions
    private val SDCARD_REQUEST_CODE = 991
    private val BT_REQUEST_CODE = 992

    // dump file name
    private val LOG_FILENAME = "stepcounter"
    private var logwriter: FileWriter? = null

    // Internal state
    private var isEnabled = false
    private var logToFile = false

    // Step Counter objects
    private var stepCounter: StepCounter? = null
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var hwstepsCounter: Sensor? = null

    // Ground Truth device objets
    private var gtdevice: GroundTruthDevice? = null

    private var gtConnectingDialog: ProgressDialog? = null

    private var currentSteps = 0
    private var gtsteps = 0
    private var lastSteps = -1
    private var hwsteps = 0
    private val logFileSwitchListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Get permissions
                val permission: Int = ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        SDCARD_REQUEST_CODE
                    )
                } else {
                    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()) {
                        logToFile = true
                    }
                }
            } else {
                logToFile = false
            }
        }

    private val groundtruthSwitchListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Get permissions
                val btpermission: Int = ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH
                )
                val btadminpermission: Int = ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_ADMIN
                )
                val locationpermission: Int = ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (btpermission != PackageManager.PERMISSION_GRANTED ||
                    btadminpermission != PackageManager.PERMISSION_GRANTED ||
                    locationpermission != PackageManager.PERMISSION_GRANTED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        BT_REQUEST_CODE
                    )
                } else {
                    gtConnectingDialog!!.show()
                    gtdevice!!.connect()
                }
            } else {
                gtConnectingDialog!!.cancel()
                gtdevice!!.disconnect()
            }
        }


    private val startClickListener = View.OnClickListener {
        if (isEnabled) {
            // Stop sampling
            sensorManager!!.unregisterListener(accelerometerEventListener)
            if (hwstepsCounter != null) sensorManager!!.unregisterListener(hwStepsEventListener)

            // Stop algorithm.
            isEnabled = false
            binding.btnToggleStepCounter.isEnabled = false
            binding.btnToggleStepCounter.text = "Start Step Counting"
            stepCounter?.stop()
            try {
                if (logwriter != null) logwriter?.close()
            } catch (e: IOException) {
            }
        } else {
            // Start algorithm.
            binding.stepsTextView.text = "Steps: 0"
            binding.gtTextView.text = "Ground truth: 0"
            if (hwstepsCounter != null) binding.hwstepsTextView.text = "HW steps: 0"
            isEnabled = true
            currentSteps = 0
            gtsteps = 0
            hwsteps = 0
            lastSteps = -1
            stepCounter?.start()
            binding.btnToggleStepCounter.text = "Stop Step Counting"

            // start data log
            if (logToFile) {
                val filepath = Environment.getExternalStorageDirectory()
                    .toString() + "/" + LOG_FILENAME + "_" + System.currentTimeMillis() + ".csv"
                try {
                    logwriter = FileWriter(filepath)
                } catch (ex: IOException) {
                    Toast.makeText(this, "Cannot create log file", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            // Start sampling
            val periodusecs = (1E6 / SAMPLING_FREQUENCY).toInt()
            Log.d(
                MainActivity::class.java.simpleName,
                "Sampling at $periodusecs usec"
            )
            sensorManager!!.registerListener(accelerometerEventListener, accelerometer, periodusecs)
            if (hwstepsCounter != null) sensorManager!!.registerListener(
                hwStepsEventListener,
                hwstepsCounter,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions?.let { super.onRequestPermissionsResult(requestCode, it, grantResults) }
        when (requestCode) {
            SDCARD_REQUEST_CODE -> {
                if (allPermissionsGranted(grantResults) &&
                    (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState())
                ) {
                    // permission was granted and sd card is writeable
                    logToFile = true
                } else {
                    logToFile = false
                    binding.logSwitch!!.isChecked = false
                }
                return
            }

            BT_REQUEST_CODE -> {
                if (allPermissionsGranted(grantResults)) {
                    gtdevice!!.connect()
                } else {
                    Toast.makeText(this, "Cannot get permissions for Bluetooth", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
        }
    }

    private fun allPermissionsGranted(grantResults: IntArray): Boolean {
        for (p in grantResults) {
            if (p != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    private val accelerometerEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            stepCounter?.processSample(event.timestamp, event.values)
            if (logToFile && logwriter != null) {
                var logline = event.timestamp.toString() + ","
                for (`val` in event.values) logline += "$`val`,"
                logline += "$currentSteps,"
                logline += "$gtsteps,"
                logline += hwsteps
                logline += "\n"
                try {
                    logwriter?.append(logline)
                } catch (e: IOException) {
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private val hwStepsEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val steps = event.values[0].toInt()
            if (lastSteps == -1) {
                hwsteps = 0
                lastSteps = steps
            } else {
                hwsteps = steps - lastSteps
            }
            Log.d(
                MainActivity::class.java.simpleName,
                "HW steps: $steps, i.e. $hwsteps"
            )
            runOnUiThread {
                val hwstr = "HW steps: $hwsteps"
                binding.hwstepsTextView.text = hwstr
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private val gtdeviceCallback: GroundTruthDevice.GTdeviceCallback = object : GroundTruthDevice.GTdeviceCallback {
        override fun connected() {
            runOnUiThread {
                gtConnectingDialog!!.cancel()
                gtsteps = 0
                binding.gtTextView!!.text = "Ground truth: $gtsteps"
                binding.gtdeviceSwitch!!.isChecked = true
            }
        }

        override  fun disconnected() {
            runOnUiThread {
//                Toast.makeText(this, "Device disconnected", Toast.LENGTH_SHORT).show()
                gtConnectingDialog!!.cancel()
                binding.gtdeviceSwitch!!.isChecked = false
            }
        }

        override  fun stepDetected(left: Boolean, right: Boolean) {
            runOnUiThread {
                if (left != right) {
                    gtsteps++
                    binding.gtTextView!!.text = "Ground truth: $gtsteps"
                }
            }
        }
    }
    override fun init() {

        // Keep screen on.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.btnToggleStepCounter.setOnClickListener(startClickListener)
        binding.logSwitch.setChecked(false)
        binding.logSwitch.setOnCheckedChangeListener(logFileSwitchListener)
        binding.gtdeviceSwitch.setChecked(false)
        binding.gtdeviceSwitch.setOnCheckedChangeListener(groundtruthSwitchListener)
        gtConnectingDialog = ProgressDialog(this)
        gtConnectingDialog?.setTitle("Connecting")
        gtConnectingDialog?.setMessage("Trying to connect to the ground truth device...")
        gtConnectingDialog?.setCancelable(false) // disable dismiss by tapping outside of the dialog


        // Initialize step counter
        sensorManager = this.getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        hwstepsCounter = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounter = StepCounter(SAMPLING_FREQUENCY.toFloat())
        stepCounter?.addOnStepUpdateListener { steps ->
            runOnUiThread {
                currentSteps = steps
                val text = "Steps: " + Integer.toString(currentSteps)
                binding.stepsTextView.text = text
            }
        }
        stepCounter?.setOnFinishedProcessingListener {
            runOnUiThread {
                binding.btnToggleStepCounter.isEnabled = true
            }
        }

        // Init Ground Truth device
        gtdevice = GroundTruthDevice(gtdeviceCallback, this@StepNumberActivity)
    }
}