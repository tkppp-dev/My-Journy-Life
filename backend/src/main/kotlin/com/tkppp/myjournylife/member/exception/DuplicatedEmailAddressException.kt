package com.tkppp.myjournylife.member.exception

import java.lang.Exception

class DuplicatedEmailAddressException(message: String = "EmailAddress is duplicated")
    : Exception(message){
}