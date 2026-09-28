package com.example.unidad7ruta2ejercicio2.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (apiKey.isBlank()) {
            return chain.proceed(originalRequest)
        }
        val requestWithKey = originalRequest.newBuilder()
            .url(originalRequest.url.newBuilder().addQueryParameter("key", apiKey).build())
            .build()
        return chain.proceed(requestWithKey)
    }
}
