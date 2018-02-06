package com.mateuszbartos.theguard.api

import com.mateuszbartos.theguard.GsonProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val baseUrl = "http://52.236.165.15/backend/"
    private var apiService: ApiService? = null

    fun initApi() {
        val httpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.get()))
                .client(httpClient.build())
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun get(): ApiService {
        checkNotNull(apiService)
        return apiService!!
    }
}
