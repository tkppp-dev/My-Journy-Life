package com.tkppp.myjournylife.member.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/login")
class LoginController {

    @GetMapping("/success/{jwt}")
    fun loginSuccess(@PathVariable jwt: String): String{
        return jwt
    }

    @GetMapping("/fail")
    fun loginFail(): String {
        return "fail"
    }

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}