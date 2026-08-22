package com.samsung.android.scan3d.util

import java.net.NetworkInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IpUtil {

    suspend fun getLocalIpAddress(): String? = withContext(Dispatchers.IO) {
        try {
            return@withContext NetworkInterface.getNetworkInterfaces().asSequence()
                .filter { it.isUp && !it.isLoopback }
                .flatMap { it.inetAddresses.asSequence() }
                .find { !it.isLoopbackAddress && it.isSiteLocalAddress }
                ?.hostAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }
}
