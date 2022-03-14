package com.tkppp.myjournylife.member.exception

class InvalidAccessTokenException(message: String = "Delivered access token is invalid.")
    : Exception(message) {}