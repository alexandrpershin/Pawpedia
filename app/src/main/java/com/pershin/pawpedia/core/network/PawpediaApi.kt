package com.pershin.pawpedia.core.network

import com.pershin.pawpedia.core.network.baseresponse.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PawpediaApi {

    @GET("api/breeds/list/all")
    suspend fun getPawBreedList(): Response<BaseResponse<Map<String, List<String>>>>

    @GET("api/breed/{breed}/{subbreed}/images/random/{count}")
    suspend fun geSubBreedImages(
        @Path(value = "breed") breed: String,
        @Path(value = "subbreed") subbreed: String,
        @Path(value = "count") count: Int,
    ): Response<BaseResponse<List<String>>>

    @GET("api/breed/{breed}/images/random/{count}")
    suspend fun geBreedImages(
        @Path(value = "breed") breed: String,
        @Path(value = "count") count: Int,
    ): Response<BaseResponse<List<String>>>
}

