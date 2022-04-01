package com.tkppp.myjournylife.error

import com.tkppp.myjournylife.dto.ErrorResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(value = [CustomException::class])
    fun handlingCustomException(ex: CustomException): ResponseEntity<ErrorResponseDto> {
        val errorCode: ErrorCode = ex.errorCode
        val errorDto = ErrorResponseDto(errorCode = errorCode.name, message = errorCode.message)
        log.error("${errorCode.name} - ${errorCode.message}")
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handlingInsufficientAuthenticationException(ex: Exception): ResponseEntity<ErrorResponseDto> {
        val errorCode = ErrorCode.INSUFFICIENT_AUTHENTICATION
        val errorDto = ErrorResponseDto(errorCode.name, "${errorCode.message} - ${errorCode.message}")
        log.error("${errorCode.name} - ${errorCode.message}")
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handlingGlobalException(ex: Exception): ResponseEntity<ErrorResponseDto> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val errorDto = ErrorResponseDto(errorCode.name, "${errorCode.message} - ${ex.message}")
        ex.printStackTrace()
        return ResponseEntity(errorDto, errorCode.status)
    }
}