package com.tkppp.myjournylife.error

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException()