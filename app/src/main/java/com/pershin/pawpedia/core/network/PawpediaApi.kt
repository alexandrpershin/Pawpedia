package com.pershin.pawpedia.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PawpediaApi {

    @GET("api/offers")
    suspend fun getPawBreedList(): Response<Any>

    @GET("api/breed/{breed}/{subbreed}/images")
    suspend fun geSubBreedImages(
        @Path(value = "breed") breed: String,
        @Path(value = "subbreed") subbreed: String,
        ): Response<BaseResponse<List<String>>>

    @GET("api/breed/{breed}/images")
    suspend fun geBreedImages(
        @Path(value = "breed") breed: String,
    ): Response<BaseResponse<List<String>>>
}

// TODO move it
open class BaseResponse<T>(
    val status: String,
    val message: T,
    val code: Int? = null
)

// TODO move it
class BreedDto(
    val name: String
)
