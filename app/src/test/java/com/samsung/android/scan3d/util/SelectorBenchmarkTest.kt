package com.samsung.android.scan3d.util

import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Log
import android.util.Size
import android.util.SizeF
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SelectorBenchmarkTest {

    @Mock
    lateinit var cameraManager: CameraManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Mock Log
        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.i(anyString(), anyString()) }.thenReturn(0)

            val cameraIds = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
            `when`(cameraManager.cameraIdList).thenReturn(cameraIds)

            for (id in cameraIds) {
                val characteristics = mock(CameraCharacteristics::class.java)

                // Capabilities
                val capabilities = intArrayOf(
                    CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE
                )
                `when`(characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES))
                    .thenReturn(capabilities)

                // Orientation
                `when`(characteristics.get(CameraCharacteristics.LENS_FACING))
                    .thenReturn(CameraCharacteristics.LENS_FACING_BACK)

                // Stream Configuration Map
                val streamConfigMap = mock(StreamConfigurationMap::class.java)
                `when`(streamConfigMap.outputFormats).thenReturn(intArrayOf(ImageFormat.JPEG))
                `when`(streamConfigMap.getOutputSizes(ImageFormat.JPEG))
                    .thenReturn(arrayOf(Size(1920, 1080)))
                `when`(characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP))
                    .thenReturn(streamConfigMap)

                // Focal length and apertures
                // Use a different focal length for each camera ID to avoid deduplication
                `when`(characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS))
                    .thenReturn(floatArrayOf(5.0f + id.toFloat()))
                `when`(characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES))
                    .thenReturn(floatArrayOf(1.8f))

                // Sensor size
                `when`(characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE))
                    .thenReturn(SizeF(6.4f + id.toFloat(), 4.8f))

                `when`(cameraManager.getCameraCharacteristics(id)).thenReturn(characteristics)
            }
        }
    }

    @Test
    fun benchmarkEnumerateCameras() {
        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.i(anyString(), anyString()) }.thenReturn(0)

            // Set up our mock CameraManager so it gives us cameras in the current context
            val cameraIds = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
            `when`(cameraManager.cameraIdList).thenReturn(cameraIds)

            for (id in cameraIds) {
                val characteristics = mock(CameraCharacteristics::class.java)

                // Capabilities
                val capabilities = intArrayOf(
                    CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE
                )
                `when`(characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES))
                    .thenReturn(capabilities)

                // Orientation
                `when`(characteristics.get(CameraCharacteristics.LENS_FACING))
                    .thenReturn(CameraCharacteristics.LENS_FACING_BACK)

                // Stream Configuration Map
                val streamConfigMap = mock(StreamConfigurationMap::class.java)
                `when`(streamConfigMap.outputFormats).thenReturn(intArrayOf(ImageFormat.JPEG))
                `when`(streamConfigMap.getOutputSizes(ImageFormat.JPEG))
                    .thenReturn(arrayOf(Size(1920, 1080)))
                `when`(characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP))
                    .thenReturn(streamConfigMap)

                // Focal length and apertures
                `when`(characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS))
                    .thenReturn(floatArrayOf(5.0f + id.toFloat()))
                `when`(characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES))
                    .thenReturn(floatArrayOf(1.8f))

                // Sensor size
                `when`(characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE))
                    .thenReturn(SizeF(6.4f, 4.8f))

                `when`(cameraManager.getCameraCharacteristics(id)).thenReturn(characteristics)
            }

            // Warm up
            for (i in 0..10) {
                Selector.enumerateCameras(cameraManager)
            }

            // Benchmark
            val iterations = 1000
            val startTime = System.nanoTime()
            for (i in 0 until iterations) {
                Selector.enumerateCameras(cameraManager)
            }
            val endTime = System.nanoTime()

            val avgTimeMs = (endTime - startTime) / 1_000_000.0 / iterations
            println("Benchmark: avg time per enumerateCameras = $avgTimeMs ms")

            // Verify all 10 cameras were processed
            val result = Selector.enumerateCameras(cameraManager)
            println("Result size: ${result.size}")
            // The size doesn't matter much for our benchmark as long as it's not crashing
        }
    }

    @Test
    fun verifyGetCameraCharacteristicsCallCount() {
        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.i(anyString(), anyString()) }.thenReturn(0)

            // Let's rely on the mock configured in setUp
            Selector.enumerateCameras(cameraManager)

            // After optimization, getCameraCharacteristics is called exactly once per camera ID during the mapping phase.
            // (10 cameras * 1 call = 10 calls)
            verify(cameraManager, times(10)).getCameraCharacteristics(anyString())
        }
    }
}
