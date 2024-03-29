package com.example.jalwa.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred

suspend fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.suspendQuery(query: Query<D, T, V>): Response<T> =
    query(query).toDeferred().await()

suspend fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.suspendMutate(mutation: Mutation<D, T, V>): Response<T> =
    mutate(mutation).toDeferred().await()