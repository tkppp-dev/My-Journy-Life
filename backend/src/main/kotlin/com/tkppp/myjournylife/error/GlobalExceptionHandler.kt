package com.tkppp.myjournylife.error

import com.tkppp.myjournylife.dto.ErrorResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [CustomException::class])
    fun handlingCustomException(ex: CustomException): ResponseEntity<ErrorResponseDto> {
        val errorCode: ErrorCode = ex.errorCode
        val errorDto = ErrorResponseDto(errorCode = errorCode.name, message = errorCode.message)
        return ResponseEntity(errorDto, errorCode.status)
    }
}