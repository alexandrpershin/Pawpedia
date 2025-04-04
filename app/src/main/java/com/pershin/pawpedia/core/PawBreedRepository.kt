package com.pershin.pawpedia.core

import com.pershin.pawpedia.feature.pawlist.domain.PawBreed

interface PawBreedRepository {

    suspend fun getPawBreeds() : Result<List<PawBreed>>

    suspend fun getPawBreedImages() : Result<List<String>>

}

