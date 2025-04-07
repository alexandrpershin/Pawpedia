package com.pershin.pawpedia.feature.common.repository

import com.pershin.pawpedia.feature.pawlist.domain.PawBreed

interface PawBreedRepository {

    suspend fun getPawBreeds(): List<PawBreed>

    suspend fun getPawBreedImages(breedName: String, count: Int): List<String>
}

