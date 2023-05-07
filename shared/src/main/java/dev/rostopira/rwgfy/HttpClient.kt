package dev.rostopira.rwgfy

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

object HttpClient: Interceptor {
    var userAgent = "Android; dev.rostopira.rwgfy"

    val okHttp by lazy {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(this)
            .build()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain
            .request()
            .newBuilder()
            .addHeader("User-Agent", userAgent)
            .build()
        return chain.proceed(modifiedRequest)
    }
}
