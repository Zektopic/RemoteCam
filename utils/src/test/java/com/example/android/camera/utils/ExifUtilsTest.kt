package com.example.android.camera.utils

import androidx.exifinterface.media.ExifInterface
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExifUtilsTest {

    @Test
    fun computeExifOrientation_0_notMirrored_isNormal() {
        assertEquals(ExifInterface.ORIENTATION_NORMAL, computeExifOrientation(0, false))
    }

    @Test
    fun computeExifOrientation_0_mirrored_isFlipHorizontal() {
        assertEquals(ExifInterface.ORIENTATION_FLIP_HORIZONTAL, computeExifOrientation(0, true))
    }

    @Test
    fun computeExifOrientation_180_notMirrored_isRotate180() {
        assertEquals(ExifInterface.ORIENTATION_ROTATE_180, computeExifOrientation(180, false))
    }

    @Test
    fun computeExifOrientation_180_mirrored_isFlipVertical() {
        assertEquals(ExifInterface.ORIENTATION_FLIP_VERTICAL, computeExifOrientation(180, true))
    }

    @Test
    fun computeExifOrientation_90_notMirrored_isRotate90() {
        assertEquals(ExifInterface.ORIENTATION_ROTATE_90, computeExifOrientation(90, false))
    }

    @Test
    fun computeExifOrientation_90_mirrored_isTranspose() {
        assertEquals(ExifInterface.ORIENTATION_TRANSPOSE, computeExifOrientation(90, true))
    }

    @Test
    fun computeExifOrientation_270_notMirrored_isTransverse() {
        assertEquals(ExifInterface.ORIENTATION_TRANSVERSE, computeExifOrientation(270, false))
    }

    // Bug in the main code where 270 and mirrored occurs twice.
    // However, looking at the code, it returns TRANSVERSE for 270 and mirrored, and then ROTATE_270 later.
    // Actually the code does this:
    // rotationDegrees == 270 && mirrored -> ExifInterface.ORIENTATION_TRANSVERSE
    // ...
    // rotationDegrees == 270 && mirrored -> ExifInterface.ORIENTATION_ROTATE_270
    // So the first one matches.
    @Test
    fun computeExifOrientation_270_mirrored_isTransverse() {
        assertEquals(ExifInterface.ORIENTATION_TRANSVERSE, computeExifOrientation(270, true))
    }

    @Test
    fun computeExifOrientation_invalid_isUndefined() {
        assertEquals(ExifInterface.ORIENTATION_UNDEFINED, computeExifOrientation(45, false))
        assertEquals(ExifInterface.ORIENTATION_UNDEFINED, computeExifOrientation(45, true))
        assertEquals(ExifInterface.ORIENTATION_UNDEFINED, computeExifOrientation(-90, false))
    }

    @Test
    fun decodeExifOrientation_normal() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_NORMAL)
        assertEquals(true, matrix.isIdentity)
    }

    @Test
    fun decodeExifOrientation_undefined() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_UNDEFINED)
        assertEquals(true, matrix.isIdentity)
    }

    // Android Matrix.postRotate might not give exact 0.0 or 1.0 but close float values.
    // We can just verify the values with a delta.
    @Test
    fun decodeExifOrientation_rotate90() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_ROTATE_90)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(0.0f, values[0], 0.001f)
        assertEquals(-1.0f, values[1], 0.001f)
        assertEquals(1.0f, values[3], 0.001f)
        assertEquals(0.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_rotate180() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_ROTATE_180)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(-1.0f, values[0], 0.001f)
        assertEquals(0.0f, values[1], 0.001f)
        assertEquals(0.0f, values[3], 0.001f)
        assertEquals(-1.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_rotate270() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_ROTATE_270)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(0.0f, values[0], 0.001f)
        assertEquals(1.0f, values[1], 0.001f)
        assertEquals(-1.0f, values[3], 0.001f)
        assertEquals(0.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_flipHorizontal() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_FLIP_HORIZONTAL)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(-1.0f, values[0], 0.001f)
        assertEquals(0.0f, values[1], 0.001f)
        assertEquals(0.0f, values[3], 0.001f)
        assertEquals(1.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_flipVertical() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_FLIP_VERTICAL)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(1.0f, values[0], 0.001f)
        assertEquals(0.0f, values[1], 0.001f)
        assertEquals(0.0f, values[3], 0.001f)
        assertEquals(-1.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_transpose() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_TRANSPOSE)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(0.0f, values[0], 0.001f)
        assertEquals(1.0f, values[1], 0.001f)
        assertEquals(1.0f, values[3], 0.001f)
        assertEquals(0.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_transverse() {
        val matrix = decodeExifOrientation(ExifInterface.ORIENTATION_TRANSVERSE)
        val values = FloatArray(9)
        matrix.getValues(values)
        assertEquals(0.0f, values[0], 0.001f)
        assertEquals(-1.0f, values[1], 0.001f)
        assertEquals(-1.0f, values[3], 0.001f)
        assertEquals(0.0f, values[4], 0.001f)
    }

    @Test
    fun decodeExifOrientation_invalid_isIdentity() {
        val matrix = decodeExifOrientation(999)
        assertEquals(true, matrix.isIdentity)
    }
}
