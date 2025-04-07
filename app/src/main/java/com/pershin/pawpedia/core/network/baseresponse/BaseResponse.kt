package com.pershin.pawpedia.core.network.baseresponse

open class BaseResponse<T>(
    val status: String,
    val message: T,
    val code: Int? = null,
)
