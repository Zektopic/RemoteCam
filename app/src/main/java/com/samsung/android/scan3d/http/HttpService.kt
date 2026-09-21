package com.samsung.android.scan3d.http

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.io.OutputStream

class HttpService {
    lateinit var engine: NettyApplicationEngine
    var channel = Channel<Pair<ByteArray, Int>>(2)
    fun producer(): suspend OutputStream.() -> Unit = {
        val o = this
        channel = Channel()
        val header = "--FRAME\r\nContent-Type: image/jpeg\r\n\r\n".toByteArray()
        channel.consumeEach { (bytes, len) ->
            o.write(header)
            o.write(bytes, 0, len)
            o.flush()
        }
    }
    public fun main() {
        engine = embeddedServer(Netty, port = 8080) {
            routing {
                get("/cam") {
                    call.respondText("Ok")
                }
                get("/cam.mjpeg") {
                    call.respondOutputStream(
                        ContentType.parse("multipart/x-mixed-replace;boundary=FRAME"),
                        HttpStatusCode.OK, producer()
                    )
                }
            }
        }
        engine.start(wait = false)
    }

}