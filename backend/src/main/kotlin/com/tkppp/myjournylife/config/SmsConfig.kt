package com.tkppp.myjournylife.config

import net.nurigo.java_sdk.api.Message
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SmsConfig {

    @Bean
    fun smsService(): Message {
        val apiKey = "NCSI2TESYLXCUKOS"
        val apiSecret = "QGTOWU3PMFD2VPXCIOWC37BGDBVYNTTK"
        return Message(apiKey, apiSecret)
    }
}