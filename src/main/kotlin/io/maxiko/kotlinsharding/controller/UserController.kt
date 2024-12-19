package io.maxiko.kotlinsharding.controller

import io.maxiko.kotlinsharding.model.UserRequest
import io.maxiko.kotlinsharding.service.UserService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @RequestMapping(method = [RequestMethod.POST], path= ["/post"])
    fun post(@RequestBody userRequest: UserRequest){
        userService.saveUser(userRequest)
    }

}

