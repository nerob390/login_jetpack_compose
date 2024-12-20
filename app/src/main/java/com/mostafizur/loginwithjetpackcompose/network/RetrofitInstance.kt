package com.mostafizur.loginwithjetpackcompose.network


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        val BASE_URL = "https://dummyjson.com/user/"

        var retrofit :  Retrofit? = null
        private val watchDog by lazy {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            HttpLoggingInterceptor { message ->
                // Parse the message as JSON and format it with Gson
                try {
                    val json = gson.fromJson(message, Any::class.java)
                    // Log the pretty-printed JSON
                    println(gson.toJson(json))
                } catch (e: Exception) {
                    // If there's an error during JSON parsing, log the original message
                    println(message)
                }
            }.apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(watchDog)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        fun getRetrofitInstance() : Retrofit {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!
        }
    }

    fun getApi() : ApiServices{
        return retrofit!!.create(ApiServices::class.java)
    }

}