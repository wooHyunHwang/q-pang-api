package com.qpang.orderservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok().body("order")
    }

    @GetMapping("/order/test")
    fun order(): ResponseEntity<String> {
        return ResponseEntity.ok().body("/order/test")
    }

}