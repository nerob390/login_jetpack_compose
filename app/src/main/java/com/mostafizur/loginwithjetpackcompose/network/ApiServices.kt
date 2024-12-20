package com.mostafizur.loginwithjetpackcompose.network

import com.google.gson.JsonObject
import com.mostafizur.loginwithjetpackcompose.ui.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiServices {

    @POST("login")
    suspend fun login(@Body jsonObject: JsonObject): Response<User>

}