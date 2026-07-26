package com.samsung.android.scan3d.util

import android.hardware.camera2.CameraManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

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
        assertEquals("Invalid index", Selector.getCapStringAtIndex(Int.MIN_VALUE))
    }

    @Test
    fun getCapStringAtIndex_outOfBoundsPositiveIndex_returnsInvalidIndex() {
        assertEquals("Invalid index", Selector.getCapStringAtIndex(21))
        assertEquals("Invalid index", Selector.getCapStringAtIndex(100))
        assertEquals("Invalid index", Selector.getCapStringAtIndex(Int.MAX_VALUE))
    }

    @Test
    fun enumerateCameras_cameraManagerThrowsException_returnsEmptyList() {
        val cameraManager = mock(CameraManager::class.java)
        `when`(cameraManager.cameraIdList).thenThrow(RuntimeException("Camera access failed"))

        val result = Selector.enumerateCameras(cameraManager)

        assertTrue(result.isEmpty())
    }
}
