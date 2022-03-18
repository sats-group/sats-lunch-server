package com.sats.lunch

import com.pspdfkit.api.PSPDFKit
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.sats.lunch.plugins.*

fun main() {
    PSPDFKit.initializeTrial()

    embeddedServer(Netty, port = System.getenv("PORT").toIntOrNull() ?: 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
