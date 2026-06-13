package com.samsung.android.scan3d.util

import org.junit.Assert.assertEquals
import org.junit.Test

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
}
