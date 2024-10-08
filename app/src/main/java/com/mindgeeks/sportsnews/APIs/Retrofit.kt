package com.mindgeeks.sportsnews.APIs

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

//    val BASE_URL = "http://192.168.1.46:5000/api/v1/"
    val BASE_URL = "https://cricnews.app/api/v1/"
//    val BASE_URL = " https://c310-2409-40d6-a-4247-adb6-8592-9bc6-3b11.ngrok-free.app/api/v1/"

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(60, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // Create Retrofit instance using the OkHttpClient with logging interceptor
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(com.mindgeeks.sportsnews.APIs.Retrofit.BASE_URL)
            .client(com.mindgeeks.sportsnews.APIs.Retrofit.provideOkHttpClient())

            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}