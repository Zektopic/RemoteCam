package com.samsung.android.scan3d.serv

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.Surface
import androidx.core.app.NotificationCompat
import com.samsung.android.scan3d.CameraActivity
import com.samsung.android.scan3d.R
import com.samsung.android.scan3d.ViewState
import com.samsung.android.scan3d.http.HttpService
<<<<<<< HEAD
import com.samsung.android.scan3d.util.SettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
=======
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
>>>>>>> main

class Cam : Service(), CoroutineScope {
    var engine: CamEngine? = null
    var http: HttpService? = null
<<<<<<< HEAD
    private val channelId = "REMOTE_CAM"
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
=======
    val CHANNEL_ID = "REMOTE_CAM"

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val cameraMutex = Mutex()

>>>>>>> main

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("cam", "onstartcommand " + intent?.action)

        if (intent == null) return START_STICKY

        // Security: if the service is not yet initialized, ignore commands except "start"
        if (http == null && intent.action != "start") {
            Log.w("cam", "service not yet initialized (http=null). command '${intent.action}' ignored.")
            return START_STICKY
        }

        when (intent.action) {
            "start" -> {
<<<<<<< HEAD
                if (http == null) {
                    val channel = NotificationChannel(
                        channelId,
                        channelId,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    channel.description = "remotecam run"
                    val notificationManager = getSystemService(NotificationManager::class.java)
                    notificationManager.createNotificationChannel(channel)
=======
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = getString(R.string.notification_channel_desc)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
>>>>>>> main

                    val notificationIntent = Intent(this, CameraActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(
                        this,
                        System.currentTimeMillis().toInt(),
                        notificationIntent,
                        FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                    )

                    val intentKill = Intent(this, Cam::class.java)
                    intentKill.action = "KILL"

                    val pendingIntentKill = PendingIntent.getService(
                        this,
                        System.currentTimeMillis().toInt(),
                        intentKill,
                        FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
                    )

<<<<<<< HEAD
                    val builder =
                        NotificationCompat.Builder(this, channelId)
                            .setContentTitle(getString(R.string.notif_title))
                            .setContentText(getString(R.string.notif_text))
                            .setOngoing(true)
                            .setSmallIcon(R.drawable.ic_linked_camera)
                            .addAction(R.drawable.ic_close, getString(R.string.notif_kill), pendingIntentKill)
                            .setContentIntent(pendingIntent)
=======
                val builder =
                    NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_text)).setOngoing(true)
                        .setSmallIcon(R.drawable.ic_linked_camera).addAction(R.drawable.ic_close, getString(R.string.notification_action_stop), pendingIntentKill)
                        .setContentIntent(pendingIntent)
>>>>>>> main

                    val notification: Notification = builder.build()

                    startForeground(123, notification)

<<<<<<< HEAD
                    http = HttpService(this)
                    // engine?.http = http // Will be assigned when the engine is created

                    launch(Dispatchers.IO) {
                        http?.start()
                    }
                }
            }

            "onPause" -> {
                val allowBackground = SettingsManager.loadBackgroundStreaming(this)
                engine?.insidePause = !allowBackground
=======
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    //      builder.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
                }
                val notification: Notification = builder.build()
                startForeground(123, notification) // Start the foreground service

                http = HttpService()
                http?.main()

            }

            "onPause" -> {
                serviceScope.launch {
                    cameraMutex.withLock {
                        engine?.insidePause = true
                        if (engine?.isShowingPreview == true) {
                            engine?.restart()
                        }
                    }
                }
>>>>>>> main
            }

            "onResume" -> {
                engine?.insidePause = false
            }

<<<<<<< HEAD
            "start_engine_with_surface" -> {
                val surface: Surface? = intent.extras?.getParcelable("surface", Surface::class.java)

                engine?.let {
                    if (it.insidePause) it.insidePause = false
                }

                if (engine == null) {
                    val targetFps = SettingsManager.loadTargetFps(this)
                    val antiFlicker = SettingsManager.loadAntiFlickerMode(this)
                    val noiseReduction = SettingsManager.loadNoiseReductionMode(this)
                    val stabilizationOff = SettingsManager.loadStabilizationOff(this)

                    engine = CamEngine(
                        context = this,
                        targetFps = targetFps,
                        currentAntiFlickerMode = antiFlicker,
                        currentNoiseReductionMode = noiseReduction,
                        isStabilizationOff = stabilizationOff
                    )
                    engine?.http = http
                }

                engine?.previewSurface = surface
                engine?.initializeCamera()
            }

            "new_view_state" -> {
                engine?.let { eng ->
                    val old = eng.viewState
                    val new: ViewState = intent.extras?.getParcelable("data", ViewState::class.java)!!
                    eng.viewState = new

                    // Check if a change requires restarting the encoder/camera
                    if (old.cameraId != new.cameraId ||
                        old.resolutionIndex != new.resolutionIndex ||
                        old.preview != new.preview ||
                        old.streamFormat != new.streamFormat)
                    {
                        // Disconnect clients only if the format, resolution or sensor changes
                        // to force OBS to redetect the image geometry.
                        val shouldDisconnect = old.cameraId != new.cameraId ||
                                old.resolutionIndex != new.resolutionIndex ||
                                old.streamFormat != new.streamFormat

                        eng.restart(disconnectClients = shouldDisconnect)
                    }
                    // Other parameters that only require a repeating request update (no restart)
                    else if (old.flash != new.flash ||
                        old.quality != new.quality ||
                        old.flashLevel != new.flashLevel ||
                        old.h264Bitrate != new.h264Bitrate ||
                        old.h264Mode != new.h264Mode)
                    {
                        // Note: The H264 bitrate requires a restart(false) to be applied by MediaCodec
                        if (old.h264Bitrate != new.h264Bitrate || old.h264Mode != new.h264Mode) {
                            eng.restart(disconnectClients = false)
                        } else {
                            eng.updateRepeatingRequest()
=======
            "start_camera_engine" -> {
                serviceScope.launch {
                    cameraMutex.withLock {
                        val newEngine = withContext(Dispatchers.IO) {
                            CamEngine(this@Cam)
                        }
                        newEngine.http = http
                        engine = newEngine
                        newEngine.initializeCamera()
                    }
                }
            }

            "new_view_state" -> {
                val new : CameraFragment.Companion.ViewState = intent.extras?.getParcelable("data")!!
                Log.i("CAM", "new_view_state: " + new)

                serviceScope.launch {
                    cameraMutex.withLock {
                        val old = engine?.viewState
                        if (old == null) {
                            // Engine not ready or null
                            return@withLock
                        }
                        Log.i("CAM", "from:           " + old)
                        engine?.viewState = new
                        if (old != new) {
                            Log.i("CAM", "diff")
                            engine?.restart()
>>>>>>> main
                        }
                    }
                }
            }

<<<<<<< HEAD
            "preview_surface_destroyed" -> {
                engine?.previewSurface = null
                if (engine?.isShowingPreview == true) {
                    // We don't disconnect clients when the local preview dies (e.g. going to settings)
                    engine?.restart(disconnectClients = false)
=======
            "new_preview_surface" -> {
                val surface: Surface? = intent.extras?.getParcelable("surface")
                serviceScope.launch {
                    cameraMutex.withLock {
                        // Toast.makeText(this, "SURFACE", Toast.LENGTH_SHORT).show()
                        engine?.previewSurface = surface
                        if (engine?.viewState?.preview == true) {
                            engine?.initializeCamera()
                        }
                    }
>>>>>>> main
                }
            }

            "scale_zoom" -> {
                val scale = intent.getFloatExtra("scale_factor", 1.0f)
                engine?.scaleZoom(scale)
            }

            "set_zoom_ratio" -> {
                val ratio = intent.getFloatExtra("ratio", 0.0f)
                engine?.setZoomRatio(ratio)
            }

            "volume_zoom_in" -> engine?.stepZoomIn()
            "volume_zoom_out" -> engine?.stepZoomOut()
            "volume_action_switch_cam" -> engine?.switchToNextCamera()
            "volume_action_toggle_flash" -> engine?.toggleFlash()

            "set_target_fps" -> {
                val fps = intent.getIntExtra("fps", 30)
                engine?.setTargetFps(fps)
            }

            "double_tap_action_switch_camera" -> engine?.switchToNextCamera()
            "double_tap_action_toggle_zoom" -> engine?.toggleZoom()

            "set_anti_flicker" -> {
                val mode = intent.getIntExtra("mode", SettingsManager.ANTI_FLICKER_AUTO)
                engine?.setAntiFlickerMode(mode)
            }

            "set_noise_reduction" -> {
                val mode = intent.getIntExtra("mode", SettingsManager.NR_AUTO)
                engine?.setNoiseReductionMode(mode)
            }

            "set_stabilization" -> {
                val isOff = intent.getBooleanExtra("is_off", false)
                engine?.setStabilizationOff(isOff)
            }

            "set_zoom_smoothing" -> {
                val delay = intent.getIntExtra("delay", SettingsManager.SMOOTH_DELAY_NONE)
                engine?.setZoomSmoothingDelay(delay)
            }

            "set_stream_format" -> {
                val format = intent.getIntExtra("format", SettingsManager.FORMAT_MJPEG)
                engine?.setStreamFormat(format)
            }

            "set_h264_bitrate" -> {
                val bitrate = intent.getIntExtra("bitrate", SettingsManager.DEFAULT_H264_BITRATE)
                engine?.setH264Bitrate(bitrate)
            }

            "set_h264_mode" -> {
                val mode = intent.getIntExtra("mode", SettingsManager.H264_MODE_CBR)
                engine?.setH264Mode(mode)
            }

            "set_http_port" -> {
                val newPort = intent.getIntExtra("port", SettingsManager.DEFAULT_PORT)
                http?.restartServer(newPort)

                val uiIntent = Intent("PORT_UPDATED").setPackage(packageName)
                sendBroadcast(uiIntent)
                // We restart the engine to reconnect the stream to the new server
                engine?.restart(disconnectClients = true)
            }

            "set_flash_level" -> {
                val level = intent.getIntExtra("level", 1)
                engine?.setFlashLevel(level)
            }

            "KILL", "stop" -> {
                kill()
            }

            else -> {
                Log.w("cam", "unknown or unhandled action: ${intent.action}")
            }
        }

        return START_STICKY
    }

    private fun kill(){
        engine?.destroy()
        engine = null

        http?.stop()
        http = null

        val intent = Intent("KILL_ACTIVITY").setPackage(packageName)
        sendBroadcast(intent)

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
<<<<<<< HEAD
        Log.i("cam", "ondestroy")
=======
        Log.i("CAM", "OnDestroy")
        serviceScope.cancel()
>>>>>>> main
        kill()
        job.cancel()
    }
}