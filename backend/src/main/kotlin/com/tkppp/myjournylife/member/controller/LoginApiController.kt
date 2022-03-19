package com.tkppp.myjournylife.member.controller


import com.tkppp.myjournylife.dto.ErrorResponseDto
import com.tkppp.myjournylife.dto.member.login.LoginResponseDto
import com.tkppp.myjournylife.dto.member.login.ReissueAuthTokenRequestDto
import com.tkppp.myjournylife.dto.member.login.ReissueAuthTokenResponseDto
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.service.LoginService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginApiController(
    private val loginService: LoginService
) {

    @GetMapping("/success")
    fun loginSuccess(@RequestParam access: String, @RequestParam refresh: String): ResponseEntity<LoginResponseDto> {
        loginService.saveTokenAtCache(access, refresh)
        return ResponseEntity(LoginResponseDto(access, refresh), HttpStatus.OK)
    }

    @GetMapping("/fail")
    fun loginFail(): ResponseEntity<ErrorResponseDto> {
        val errorCode = ErrorCode.LOGIN_FAIL
        return ResponseEntity(
            ErrorResponseDto(
                errorCode.name, errorCode.message
            ), errorCode.status
        )
    }

    @PostMapping("/auth/reissue")
    fun reissueAuthenticationToken(@RequestBody requestBody: ReissueAuthTokenRequestDto): ResponseEntity<ReissueAuthTokenResponseDto> {
        val result = loginService.reissueAccessToken(requestBody.accessToken, requestBody.refreshToken)
        return ResponseEntity(ReissueAuthTokenResponseDto(result), HttpStatus.OK)
    }

}