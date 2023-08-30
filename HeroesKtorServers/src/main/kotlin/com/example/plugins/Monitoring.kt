package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*

fun Application.configuringMonitoring(){
    install(CallLogging)
}