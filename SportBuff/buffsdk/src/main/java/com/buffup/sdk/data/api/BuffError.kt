package com.buffup.sdk.data.api

import java.lang.Exception

sealed class BuffError: Exception() {

    class ServerError: BuffError()
    class Unknown: BuffError()

}