package com.gaming.stattracker.controllers

import com.gaming.stattracker.models.JsonUser
import com.gaming.stattracker.models.JsonUserCreateRequest
import com.gaming.stattracker.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val service: UserService) {

    @PostMapping("/users")
    fun create(@RequestBody @Valid request: JsonUserCreateRequest): ResponseEntity<JsonUser> {
        return ResponseEntity(service.create(request), HttpStatus.CREATED)
    }

}
