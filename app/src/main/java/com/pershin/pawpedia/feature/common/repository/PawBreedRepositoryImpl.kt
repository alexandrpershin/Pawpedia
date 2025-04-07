package com.pershin.pawpedia.feature.common.repository

import com.pershin.pawpedia.core.network.PawpediaApi
import com.pershin.pawpedia.feature.pawlist.domain.PawBreed
import javax.inject.Inject

class PawBreedRepositoryImpl @Inject constructor(
    private val api: PawpediaApi,
) : PawBreedRepository {
    override suspend fun getPawBreeds(): List<PawBreed> {
        return try {
            val result = api.getPawBreedList()

            if (result.isSuccessful) {
                val breedDtos = result.body()?.message
                    ?: throw Exception("Response body is null")

                breedDtos.map { (breedName, subBreeds) ->
                    PawBreed(breedName, subBreeds)
                }
            } else {
                throw Exception("Failed to fetch breed list, HTTP code: '${result.code()}'")
            }
        } catch (ex: Exception) {
            throw Exception("Error while fetching paw breed list: ${ex.message}", ex)
        }
    }

    override suspend fun getPawBreedImages(breedName: String, count: Int): List<String> {
        return try {
            val (parentBreed, childBreed) = breedName.split("/", limit = 2).let {
                it.first() to it.getOrNull(1)
            }

            val result = if (childBreed != null) {
                api.geSubBreedImages(breed = parentBreed, subbreed = childBreed, count = count)
            } else {
                api.geBreedImages(breed = breedName, count = count)
            }

            if (result.isSuccessful) {
                result.body()?.message.orEmpty()
            } else {
                throw Exception("Failed to fetch breed images, HTTP code: '${result.code()}'")
            }
        } catch (ex: Exception) {
            throw Exception("Error while fetching breed images: ${ex.message}", ex)
        }
    }
}
