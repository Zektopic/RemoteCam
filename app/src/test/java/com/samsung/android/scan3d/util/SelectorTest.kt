package com.samsung.android.scan3d.util

import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Size
import android.util.SizeF
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class SelectorTest {

    @Test
    fun getCapStringAtIndex_validIndices_returnsCorrectString() {
        // Test first valid index
        assertEquals("BACKWARD_COMPATIBLE", Selector.getCapStringAtIndex(0))

        // Test a middle valid index
        assertEquals("MOTION_TRACKING", Selector.getCapStringAtIndex(10))

        // Test last valid index
        assertEquals("COLOR_SPACE_PROFILES", Selector.getCapStringAtIndex(20))
    }

    @Test
    fun getCapStringAtIndex_negativeIndex_returnsInvalidIndex() {
        assertEquals("Invalid index", Selector.getCapStringAtIndex(-1))
        assertEquals("Invalid index", Selector.getCapStringAtIndex(-100))
    }

    @Test
    fun getCapStringAtIndex_outOfBoundsPositiveIndex_returnsInvalidIndex() {
        assertEquals("Invalid index", Selector.getCapStringAtIndex(21))
        assertEquals("Invalid index", Selector.getCapStringAtIndex(100))
    }

    @Test
    fun enumerateCameras_withUnknownLensFacing_handlesGracefully() {
        val cameraManager = mock(CameraManager::class.java)
        val characteristics = mock(CameraCharacteristics::class.java)

        `when`(cameraManager.cameraIdList).thenReturn(arrayOf("test_camera"))
        `when`(cameraManager.getCameraCharacteristics("test_camera")).thenReturn(characteristics)

        `when`(characteristics.get(any(CameraCharacteristics.Key::class.java))).thenAnswer { invocation ->
            val key = invocation.getArgument(0) as CameraCharacteristics.Key<*>
            when (key.name) {
                "android.request.availableCapabilities" -> intArrayOf(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE)
                "android.lens.facing" -> 999 // Unknown facing
                "android.scaler.streamConfigurationMap" -> {
                    val map = mock(StreamConfigurationMap::class.java)
                    `when`(map.outputFormats).thenReturn(intArrayOf(ImageFormat.JPEG))
                    val sizeMock = mock(Size::class.java)
                    `when`(sizeMock.width).thenReturn(1920)
                    `when`(sizeMock.height).thenReturn(1080)
                    `when`(map.getOutputSizes(ImageFormat.JPEG)).thenReturn(arrayOf(sizeMock))
                    map
                }
                "android.lens.info.availableFocalLengths" -> floatArrayOf(4.0f)
                "android.lens.info.availableApertures" -> floatArrayOf(2.0f)
                "android.sensor.info.physicalSize" -> {
                    val sensorSize = mock(SizeF::class.java)
                    `when`(sensorSize.width).thenReturn(10.0f)
                    `when`(sensorSize.height).thenReturn(5.0f)
                    sensorSize
                }
                else -> null
            }
        }

        val result = Selector.enumerateCameras(cameraManager)

        assertEquals(1, result.size)
        assertTrue(result[0].title.contains("Unknown"))
    }
}
