package com.pershin.pawpedia.feature.pawlist.domain

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class EncodeUrlUseCase @Inject constructor() {

    operator fun invoke(url: String): String {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    }

}

