package com.samsung.android.scan3d.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.junit.Assert.assertEquals
import org.junit.Assert.fail

class ClipboardUtilTest {
    @Test
    fun testCopyToClipboard_success() {
        val mockContext = mock(Context::class.java)
        val mockClipboard = mock(ClipboardManager::class.java)
        val mockClipData = mock(ClipData::class.java)

        `when`(mockContext.getSystemService(Context.CLIPBOARD_SERVICE)).thenReturn(mockClipboard)

        mockStatic(ClipData::class.java).use { mockedStatic ->
            mockedStatic.`when`<ClipData> { ClipData.newPlainText("test_label", "test_text") }.thenReturn(mockClipData)

            ClipboardUtil.copyToClipboard(mockContext, "test_label", "test_text")

            val clipCaptor = ArgumentCaptor.forClass(ClipData::class.java)
            verify(mockClipboard).setPrimaryClip(clipCaptor.capture())

            assertEquals(mockClipData, clipCaptor.value)
        }
    }

    @Test
    fun testCopyToClipboard_nullContext() {
        val mockClipData = mock(ClipData::class.java)
        mockStatic(ClipData::class.java).use { mockedStatic ->
            mockedStatic.`when`<ClipData> { ClipData.newPlainText("test_label", "test_text") }.thenReturn(mockClipData)

            try {
                ClipboardUtil.copyToClipboard(null, "test_label", "test_text")
                fail("Expected Exception when context is null")
            } catch (e: Exception) {
                // If context is null, it throws NullPointerException because of `as android.content.ClipboardManager`
                assertEquals(NullPointerException::class.java, e::class.java)
            }
        }
    }
}
