package com.example.jalwa.data.api

import com.apollographql.apollo.ApolloClient

object ApolloClientManager {
    private const val SERVER_URL = "http://192.168.8.102/graphql"

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(SERVER_URL)
        .addApplicationInterceptor(AuthInterceptor())
        .build()
}
