package com.samsung.android.scan3d.serv

import android.media.MediaCodecInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.system.measureNanoTime

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CamEngineTest {

    private fun createMockCodecInfo(name: String, isEncoder: Boolean, types: Array<String>): MediaCodecInfo {
        val mockInfo = mock(MediaCodecInfo::class.java)
        `when`(mockInfo.name).thenReturn(name)
        `when`(mockInfo.isEncoder).thenReturn(isEncoder)
        `when`(mockInfo.supportedTypes).thenReturn(types)
        return mockInfo
    }

    @Test
    fun testSupportedEncodingsLogic() {
        val mockCodecs = arrayOf(
            createMockCodecInfo("decoder1", false, arrayOf("video/avc", "video/hevc")),
            createMockCodecInfo("encoder1", true, arrayOf("video/avc", "audio/mp4a-latm")),
            createMockCodecInfo("encoder2", true, arrayOf("video/hevc", "video/av01")),
            createMockCodecInfo("encoder3", true, arrayOf("video/x-vnd.on2.vp9", "unknown/type"))
        )

        val result = CamEngine.computeSupportedEncodings(mockCodecs)
        assertEquals(listOf("JPEG", "H.264", "H.265", "AV1", "VP9"), result)
    }

    @Test
    fun benchmarkSupportedEncodings() {
        // Create realistic set of MediaCodecInfo mocks (e.g., 20 codecs, 10 encoders, 5 types each)
        val codecsList = mutableListOf<MediaCodecInfo>()
        for (i in 0 until 10) {
            codecsList.add(createMockCodecInfo("decoder_$i", false, arrayOf("video/avc", "video/hevc", "audio/mp4a-latm")))
        }
        for (i in 0 until 10) {
            codecsList.add(
                createMockCodecInfo(
                    "encoder_$i",
                    true,
                    arrayOf("video/avc", "video/hevc", "video/av01", "video/x-vnd.on2.vp9", "audio/mp4a-latm")
                )
            )
        }
        val mockCodecs = codecsList.toTypedArray()

        // Warmup
        for (i in 0 until 100) {
            CamEngine.computeSupportedEncodings(mockCodecs)
        }

        val iterations = 10_000
        val elapsedNano = measureNanoTime {
            for (i in 0 until iterations) {
                CamEngine.computeSupportedEncodings(mockCodecs)
            }
        }

        val avgMs = elapsedNano / 1_000_000.0 / iterations
        println("BENCHMARK_RESULT: $iterations iterations took ${elapsedNano / 1_000_000.0} ms (Avg: $avgMs ms/op)")
        assertTrue(elapsedNano > 0)
    }
}
