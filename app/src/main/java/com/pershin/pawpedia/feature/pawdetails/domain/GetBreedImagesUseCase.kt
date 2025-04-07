package com.pershin.pawpedia.feature.pawdetails.domain

import com.pershin.pawpedia.feature.common.repository.PawBreedRepository
import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val pawBreedRepository: PawBreedRepository,
) {

    suspend operator fun invoke(breedName: String, count: Int): List<String> =
        pawBreedRepository.getPawBreedImages(breedName, count)

}
