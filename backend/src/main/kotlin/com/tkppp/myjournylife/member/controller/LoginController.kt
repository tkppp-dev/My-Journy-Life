package com.tkppp.myjournylife.member.controller


import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController {

    @GetMapping("/success")
    fun loginSuccess(@RequestParam access: String, @RequestParam refresh: String): String{
        return "accessToken : $access\nrefreshToken: $refresh"
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