package io.maxiko.kotlinsharding.service

import io.maxiko.kotlinsharding.model.UserRequest
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserServiceTest{

    @Autowired
    lateinit var userService: UserService

    @Test
    @Order(1)
    @DisplayName("testShard1")
    fun testShard1(){
        userService.saveUser(
            UserRequest(
                firstName = "Василий",
                lastName = "Табуреткин"
            )
        )
        val result = userService.getUsersByName("Василий")
        SoftAssertions.assertSoftly{
            it.assertThat(result).hasSize(1)
        }
    }

    @Test
    @Order(2)
    @DisplayName("testShard2")
    fun testShard2(){
        userService.saveUser(
            UserRequest(
                firstName = "Яков",
                lastName = "Табуреткин"
            )
        )
        val result = userService.getUsersByName("Яков")
        SoftAssertions.assertSoftly{
            it.assertThat(result).hasSize(1)
        }
    }

    @Test
    @Order(3)
    @DisplayName("getAllUsers")
    fun getAll() {
        val userList = userService.getAllUsers()
        SoftAssertions.assertSoftly {
            it.assertThat(userList.size).isEqualTo(2)
        }
    }

}