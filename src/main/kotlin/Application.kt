package com.afoxxvi

import com.afoxxvi.config.Feishu
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Feishu.init(environment.config)
    configureRouting()
}
