package com.example.jalwa.data.api

import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import java.util.concurrent.Executor

class AuthInterceptor : ApolloInterceptor {
    override fun interceptAsync(
        request: ApolloInterceptor.InterceptorRequest,
        chain: ApolloInterceptorChain,
        dispatcher: Executor,
        callBack: ApolloInterceptor.CallBack
    ) {
        chain.proceedAsync(
            request.toBuilder().requestHeaders(
                request.requestHeaders.toBuilder().addHeader(
                    HEADER_AUTHORIZATION,
                    "$HEADER_AUTHORIZATION_BEARER eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1ZmIzZjk3NzcyMGVhMTA3YzA0Njg4NGMiLCJpYXQiOjE2MDU2NDk3Mjl9.FseBjyD-Uufb7B3pe7AN4kYU4nmwBkkiee95VzkRqVM"
                ).build()
            ).build(), dispatcher, callBack
        )
    }

    override fun dispose() {}

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_AUTHORIZATION_BEARER = "Bearer"
    }
}