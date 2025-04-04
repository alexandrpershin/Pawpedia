package com.pershin.pawpedia.core

import android.util.Log
import com.pershin.pawpedia.core.network.BaseResponse
import com.pershin.pawpedia.core.network.BreedDto
import com.pershin.pawpedia.core.network.PawpediaApi
import com.pershin.pawpedia.feature.pawlist.domain.PawBreed
import retrofit2.Response
import javax.inject.Inject

class PawBreedRepositoryImpl @Inject constructor(
    private val api: PawpediaApi
) : PawBreedRepository {
    override suspend fun getPawBreeds(): Result<List<PawBreed>> {
        return try {
            val result: Response<Any> = api.getPawBreedList()

            if (result.isSuccessful) {
                Log.i("SUKA", result.body().toString())
//                TODO("Use Mapper!!!!")
                
//                val breedDtos: Map<String, List<String>> = result.body()!!.message

//                Result.success(breedDtos.map {
                    TODO("Use Mapper!!!!")
//                    PawBreed(it.name)
//                })
            } else {
                Result.failure(Exception("Something went wrong during fetching breeds, http code: '${result.code()}'"))
            }
        } catch (ex: Exception) {
            Result.failure<List<PawBreed>>(Exception("Unknown error: ${ex.message}"))
        }
    }

    override suspend fun getPawBreedImages(): Result<List<String>> {
        TODO("Not yet implemented")
    }
}