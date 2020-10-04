package com.buffup.sdk.data.api

import com.buffup.sdk.data.entity.BuffResponseEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BuffApi {

    @Headers("Accept: application/json")
    @GET("buffs/{id}")
    suspend fun getBuff(@Path("id") id: Int): BuffResponseEntity
}