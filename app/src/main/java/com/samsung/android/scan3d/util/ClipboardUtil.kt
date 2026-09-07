package com.samsung.android.scan3d.util

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.samsung.android.scan3d.R

object ClipboardUtil {

    fun copyToClipboard(context: Context?, label: String, text: String) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)

        if (context != null && Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(context, context.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }
    }
}