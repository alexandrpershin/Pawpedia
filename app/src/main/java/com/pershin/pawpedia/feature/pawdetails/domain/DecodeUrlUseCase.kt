package com.pershin.pawpedia.feature.pawdetails.domain

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class DecodeUrlUseCase @Inject constructor() {

    operator fun invoke(url: String): String {
        return URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
    }

}
