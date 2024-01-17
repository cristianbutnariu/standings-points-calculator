package com.example.footballleaguepredictor.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val baseUrl = "https://v3.football.api-sports.io/"

    fun getInstance(): APIInterface {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val authorisedRequestBuilder = originalRequest.newBuilder()
                    .addHeader("x-apisports-key", "5a320fbaa885368ba92b1b4a121130af")

                chain.proceed(authorisedRequestBuilder.build())
            }).build()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client).build()

        return retrofit.create(APIInterface::class.java)
    }
}