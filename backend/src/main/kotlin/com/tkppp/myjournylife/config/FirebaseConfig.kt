package com.tkppp.myjournylife.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @Value("\${firebase.account-path}")
    lateinit var configFilePath: String

    @PostConstruct
    fun init(){
        try{
            val serviceAccount = ClassPathResource(configFilePath)
            val opt = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.inputStream))
                .build()

            FirebaseApp.initializeApp(opt)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}