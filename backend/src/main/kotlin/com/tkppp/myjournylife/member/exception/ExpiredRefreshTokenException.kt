package com.tkppp.myjournylife.member.exception

class ExpiredRefreshTokenException(message: String = "Refresh token expired")
     : Exception(message) {}