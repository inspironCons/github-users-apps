package com.dicoding.githubusersapp.network

import com.dicoding.githubusersapp.App
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.flipper.plugins.retrofit2protobuf.SendProtobufToFlipperFromRetrofit


object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(FlipperOkhttpInterceptor(App.networkFlipperPlugin))
        .addInterceptor{ chain->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(
                    "Authorization",
                    "token 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"
                )
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service:Class<T>):T{
        SendProtobufToFlipperFromRetrofit("https://api.github.com/", service.javaClass)
        return retrofit.create(service)
    }
}