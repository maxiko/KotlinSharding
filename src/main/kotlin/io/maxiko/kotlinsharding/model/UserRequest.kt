package io.maxiko.kotlinsharding.model

data class UserRequest(
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
)