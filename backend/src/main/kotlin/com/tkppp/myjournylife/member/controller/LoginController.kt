package com.tkppp.myjournylife.member.controller


import com.tkppp.myjournylife.member.dto.login.ReissueAuthTokenReqestDto
import com.tkppp.myjournylife.member.dto.login.ReissueAuthTokenResponseDto
import com.tkppp.myjournylife.member.service.LoginService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    private val loginService: LoginService
) {

    @GetMapping("/success")
    fun loginSuccess(@RequestParam access: String, @RequestParam refresh: String): String{
        loginService.saveTokenAtCache(access, refresh)
        return "accessToken : $access\nrefreshToken: $refresh"
    }

    @GetMapping("/fail")
    fun loginFail(): String {
        return "fail"
    }

    @PostMapping("/auth/reissue")
    fun reissueAuthenticationToken(@RequestBody requestBody: ReissueAuthTokenReqestDto): ReissueAuthTokenResponseDto {
        val result = loginService.reissueAccessToken(requestBody.accessToken, requestBody.refreshToken)
        return if(result != null){
            ReissueAuthTokenResponseDto(result)
        } else {
            ReissueAuthTokenResponseDto(success = false)
        }
    }

}