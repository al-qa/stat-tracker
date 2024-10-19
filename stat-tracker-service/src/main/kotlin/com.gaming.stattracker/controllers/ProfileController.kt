package com.gaming.stattracker.controllers

import com.gaming.stattracker.models.JsonCodProfile
import com.gaming.stattracker.models.JsonCodProfileCreateRequest
import com.gaming.stattracker.models.JsonCodProfileUpdateRequest
import com.gaming.stattracker.service.CodProfileService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(val service: CodProfileService) {

    @PostMapping("/profiles")
    fun create(@RequestBody @Valid request: JsonCodProfileCreateRequest): ResponseEntity<JsonCodProfile> {
        return ResponseEntity(service.create(request), HttpStatus.CREATED)
    }

    @GetMapping("/profiles/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<JsonCodProfile> {
        return ResponseEntity(service.get(id), HttpStatus.OK)
    }

    @PutMapping("/profiles/{id}")
    fun update(@PathVariable("id") id: Long,
               @RequestBody @Valid request: JsonCodProfileUpdateRequest): ResponseEntity<JsonCodProfile> {
        return ResponseEntity(service.update(id, request), HttpStatus.OK)
    }

}
