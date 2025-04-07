package com.pershin.pawpedia.feature.common.repository

import com.pershin.pawpedia.core.network.PawpediaApi
import com.pershin.pawpedia.core.network.baseresponse.BaseResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertFails

class PawBreedRepositoryImplTest {

    private lateinit var pawBreedRepository: PawBreedRepositoryImpl
    private val api: PawpediaApi = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        pawBreedRepository = PawBreedRepositoryImpl(api)
    }

    @Test
    fun `getPawBreedImages should return list of images when successful`() = runTest {
        // Given
        val breedName = "bulldog"
        val count = 5
        val body = BaseResponse<List<String>>(
            status = "",
            message = listOf("image1.jpg", "image2.jpg", "image3.jpg"),
            code = 200
        )

        val mockResponse = mockk<Response<BaseResponse<List<String>>>>()

        every { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body() } returns body
        coEvery { api.geBreedImages(breedName, count) } returns mockResponse

        // When
        val result = pawBreedRepository.getPawBreedImages(breedName, count)

        // Then
        assertEquals(3, result.size) // 3 images returned
        assertEquals("image1.jpg", result[0])
        assertEquals("image2.jpg", result[1])
        assertEquals("image3.jpg", result[2])
    }

    @Test
    fun `getPawBreedImages should throw an exception when API fails`() = runBlocking {
        // Given
        val breedName = "bulldog"
        val count = 5
        val mockResponse = mockk<Response<BaseResponse<List<String>>>>()
        every { mockResponse.isSuccessful } returns false
        every { mockResponse.code() } returns 500
        coEvery { api.geBreedImages(breedName, count) } returns mockResponse

        // When & Then
        val exception = assertFails {
            pawBreedRepository.getPawBreedImages(breedName, count)
        }

        assertEquals(
            "Error while fetching breed images: Failed to fetch breed images, HTTP code: '500'",
            exception.message
        )
    }
}
