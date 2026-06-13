package com.samsung.android.scan3d.fragments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewState(
    var preview: Boolean,
    var stream: Boolean,
    var cameraId: String,
    var resolutionIndex: Int?,
    var quality: Int,
    var rtsp: Boolean = false,
    var encoding: String = "JPEG"
) : Parcelable
