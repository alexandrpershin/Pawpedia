package com.pershin.pawpedia.feature.pawlist.domain

import com.pershin.pawpedia.feature.common.repository.PawBreedRepository
import javax.inject.Inject

class GetPawBreedsUseCase @Inject constructor(
    private val repository: PawBreedRepository,
) {

    suspend operator fun invoke(): List<PawBreed> = repository.getPawBreeds()

}
