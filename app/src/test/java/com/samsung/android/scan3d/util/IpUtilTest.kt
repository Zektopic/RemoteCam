package com.samsung.android.scan3d.util

import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections
import java.util.Enumeration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`

class IpUtilTest {

    @Test
    fun `getLocalIpAddress returns valid site local IP address`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                val mockInterface = mock(NetworkInterface::class.java)
                val mockAddress = mock(InetAddress::class.java)

                `when`(mockAddress.isLoopbackAddress).thenReturn(false)
                `when`(mockAddress.isSiteLocalAddress).thenReturn(true)
                `when`(mockAddress.hostAddress).thenReturn("192.168.1.100")

                `when`(mockInterface.inetAddresses).thenReturn(Collections.enumeration(listOf(mockAddress)))

                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenReturn(Collections.enumeration(listOf(mockInterface)))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertEquals("192.168.1.100", ipAddress)
            }
        }
    }

    @Test
    fun `getLocalIpAddress ignores loopback addresses`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                val mockInterface = mock(NetworkInterface::class.java)
                val loopbackAddress = mock(InetAddress::class.java)
                val validAddress = mock(InetAddress::class.java)

                `when`(loopbackAddress.isLoopbackAddress).thenReturn(true)
                `when`(loopbackAddress.isSiteLocalAddress).thenReturn(true)
                `when`(loopbackAddress.hostAddress).thenReturn("127.0.0.1")

                `when`(validAddress.isLoopbackAddress).thenReturn(false)
                `when`(validAddress.isSiteLocalAddress).thenReturn(true)
                `when`(validAddress.hostAddress).thenReturn("192.168.1.101")

                `when`(mockInterface.inetAddresses).thenReturn(Collections.enumeration(listOf(loopbackAddress, validAddress)))

                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenReturn(Collections.enumeration(listOf(mockInterface)))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertEquals("192.168.1.101", ipAddress)
            }
        }
    }

    @Test
    fun `getLocalIpAddress ignores non-site-local addresses`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                val mockInterface = mock(NetworkInterface::class.java)
                val publicAddress = mock(InetAddress::class.java)
                val validAddress = mock(InetAddress::class.java)

                `when`(publicAddress.isLoopbackAddress).thenReturn(false)
                `when`(publicAddress.isSiteLocalAddress).thenReturn(false)
                `when`(publicAddress.hostAddress).thenReturn("8.8.8.8")

                `when`(validAddress.isLoopbackAddress).thenReturn(false)
                `when`(validAddress.isSiteLocalAddress).thenReturn(true)
                `when`(validAddress.hostAddress).thenReturn("10.0.0.5")

                `when`(mockInterface.inetAddresses).thenReturn(Collections.enumeration(listOf(publicAddress, validAddress)))

                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenReturn(Collections.enumeration(listOf(mockInterface)))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertEquals("10.0.0.5", ipAddress)
            }
        }
    }

    @Test
    fun `getLocalIpAddress returns null when no suitable address found`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                val mockInterface = mock(NetworkInterface::class.java)
                val publicAddress = mock(InetAddress::class.java)

                `when`(publicAddress.isLoopbackAddress).thenReturn(false)
                `when`(publicAddress.isSiteLocalAddress).thenReturn(false)

                `when`(mockInterface.inetAddresses).thenReturn(Collections.enumeration(listOf(publicAddress)))

                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenReturn(Collections.enumeration(listOf(mockInterface)))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertNull(ipAddress)
            }
        }
    }

    @Test
    fun `getLocalIpAddress returns null on exception`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenThrow(RuntimeException("Network error"))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertNull(ipAddress)
            }
        }
    }

    @Test
    fun `getLocalIpAddress returns null when no interfaces exist`() = runTest {
        withContext(Dispatchers.IO) {
            mockStatic(NetworkInterface::class.java).use { networkInterfaceStaticMock ->
                networkInterfaceStaticMock.`when`<Enumeration<NetworkInterface>> { NetworkInterface.getNetworkInterfaces() }
                    .thenReturn(Collections.enumeration(emptyList<NetworkInterface>()))

                val ipAddress = IpUtil.getLocalIpAddress()
                assertNull(ipAddress)
            }
        }
    }
}
