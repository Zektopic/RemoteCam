package com.samsung.android.scan3d.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
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
        val mockToast = mock(Toast::class.java)

        `when`(mockContext.getSystemService(Context.CLIPBOARD_SERVICE)).thenReturn(mockClipboard)

        mockStatic(Toast::class.java).use { mockedToastStatic ->
            mockedToastStatic.`when`<Toast> { Toast.makeText(any(Context::class.java), anyInt(), anyInt()) }.thenReturn(mockToast)

            mockStatic(ClipData::class.java).use { mockedStatic ->
                mockedStatic.`when`<ClipData> { ClipData.newPlainText("test_label", "test_text") }.thenReturn(mockClipData)

                ClipboardUtil.copyToClipboard(mockContext, "test_label", "test_text")

                val clipCaptor = ArgumentCaptor.forClass(ClipData::class.java)
                verify(mockClipboard).setPrimaryClip(clipCaptor.capture())

                assertEquals(mockClipData, clipCaptor.value)
            }
        }
    }

    @Test
    fun testCopyToClipboard_nullContext() {
        val mockClipData = mock(ClipData::class.java)
        mockStatic(ClipData::class.java).use { mockedStatic ->
            mockedStatic.`when`<ClipData> { ClipData.newPlainText("test_label", "test_text") }.thenReturn(mockClipData)

            // Since context is null, it returns early now due to the safe cast and null check `?: return`
            ClipboardUtil.copyToClipboard(null, "test_label", "test_text")
        }
    }
}
