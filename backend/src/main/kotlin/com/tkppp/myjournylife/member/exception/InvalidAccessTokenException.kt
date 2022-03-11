package com.tkppp.myjournylife.member.exception

class InvalidAccessTokenException(message: String = "Delivered access token is not same as stored access token.")
    : Exception(message) {}