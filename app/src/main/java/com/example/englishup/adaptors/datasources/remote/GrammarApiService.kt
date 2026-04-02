package com.example.englishup.adaptors.datasources.remote

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GrammarApiService {
    @FormUrlEncoded
    @POST("check")
    suspend fun checkGrammar(
        @Field("text") text: String,
        @Field("language") language: String = "en-US"
    ): GrammarCheckResponse
}

data class GrammarCheckResponse(
    val matches: List<GrammarMatch>
)

data class GrammarMatch(
    val message: String,
    val shortMessage: String?,
    val offset: Int,
    val length: Int,
    val context: GrammarContext,
    val replacements: List<GrammarReplacement>
)

data class GrammarContext(
    val text: String,
    val offset: Int,
    val length: Int
)

data class GrammarReplacement(
    val value: String
)
