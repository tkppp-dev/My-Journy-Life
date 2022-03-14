package com.tkppp.myjournylife.member.exception

class EmailAddressNotFoundException(message: String = "EmailAddress is not found at Database") : Exception(message) {
}