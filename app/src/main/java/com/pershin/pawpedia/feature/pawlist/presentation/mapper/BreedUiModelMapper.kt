package com.pershin.pawpedia.feature.pawlist.presentation.mapper

import com.pershin.pawpedia.feature.pawlist.domain.PawBreed
import com.pershin.pawpedia.feature.pawlist.presentation.PawListContract
import java.util.Locale
import javax.inject.Inject

class BreedUiModelMapper @Inject constructor() {

    fun map(from: List<PawBreed>): List<PawListContract.UiState.BreedUiModel> {
        return from.flatMap { breed ->
            listOf(PawListContract.UiState.BreedUiModel.Parent(breed.name.capitalize())) + breed.subBreeds.map { subBreed ->
                PawListContract.UiState.BreedUiModel.Child(
                    parentName = breed.name.capitalize(),
                    name = subBreed.capitalize()
                )
            }
        }

    }

    private fun String.capitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }

}
