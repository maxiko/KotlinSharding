package io.maxiko.kotlinsharding.dto

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<UserEntity, UUID> {

    fun findByFirstName(firstName: String): List<UserEntity>

}