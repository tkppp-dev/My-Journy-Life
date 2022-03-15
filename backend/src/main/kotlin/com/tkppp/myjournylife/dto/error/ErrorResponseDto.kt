package com.tkppp.myjournylife.dto.error

import com.tkppp.myjournylife.dto.ResponseDto

data class ErrorResponseDto(
    val errorCode: String,
    val message: String?
) : ResponseDto()