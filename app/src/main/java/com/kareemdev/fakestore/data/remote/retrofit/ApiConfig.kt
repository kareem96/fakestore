package com.kareemdev.fakestore.data.remote.retrofit

import com.kareemdev.fakestore.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val loggingInterceptor = if (BuildConfig.DEBUG){
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }else{
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)
}