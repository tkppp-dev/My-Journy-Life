package com.tkppp.myjournylife.member.controller


import com.tkppp.myjournylife.dto.ResponseDto
import com.tkppp.myjournylife.dto.error.ErrorResponseDto
import com.tkppp.myjournylife.dto.member.login.LoginResponseDto
import com.tkppp.myjournylife.dto.member.login.ReissueAuthTokenRequestDto
import com.tkppp.myjournylife.dto.member.login.ReissueAuthTokenResponseDto
import com.tkppp.myjournylife.member.exception.ExpiredRefreshTokenException
import com.tkppp.myjournylife.member.exception.InvalidAccessTokenException
import com.tkppp.myjournylife.member.service.LoginService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/login")
class LoginApiController(
    private val loginService: LoginService
) {

    @GetMapping("/success")
    fun loginSuccess(@RequestParam access: String, @RequestParam refresh: String): ResponseEntity<ResponseDto?> {
        loginService.saveTokenAtCache(access, refresh)
        return ResponseEntity.ok(LoginResponseDto(access, refresh))
    }

    @GetMapping("/fail")
    fun loginFail(): ResponseEntity<ResponseDto?>{
        return ResponseEntity(ErrorResponseDto(
            "LOGIN_FAIL", "Login Fail"
        ), HttpStatus.UNAUTHORIZED)
    }


    @PostMapping("/auth/reissue")
    fun reissueAuthenticationToken(@RequestBody requestBody: ReissueAuthTokenRequestDto): ResponseEntity<ResponseDto?> {
        return try {
            val result = loginService.reissueAccessToken(requestBody.accessToken, requestBody.refreshToken)
            ResponseEntity.ok(ReissueAuthTokenResponseDto(result))
        } catch (e: ExpiredRefreshTokenException) {
            ResponseEntity(
                ErrorResponseDto(
                    "REFRESH_TOKEN_EXPIRED", e.message
                ), HttpStatus.FORBIDDEN
            )
        } catch (e: InvalidAccessTokenException) {
            ResponseEntity(
                ErrorResponseDto(
                    "INVALID_ACCESS_TOKEN", e.message
                ), HttpStatus.FORBIDDEN
            )
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(null)
        }
    }

}