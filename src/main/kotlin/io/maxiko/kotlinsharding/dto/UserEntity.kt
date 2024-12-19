package io.maxiko.kotlinsharding.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.UUID


@Entity(name = "users")
class UserEntity(

    @Id
    @UuidGenerator
    @Column(name = "id")
    var id: UUID? = null,

    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "middle_name", nullable = true)
    var middleName: String? = null

)