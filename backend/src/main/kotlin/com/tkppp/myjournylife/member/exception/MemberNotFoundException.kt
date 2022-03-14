package com.tkppp.myjournylife.member.exception

class MemberNotFoundException(message: String = "EmailAddress is not found at Database") : Exception(message) {
}