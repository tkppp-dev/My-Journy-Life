package com.tkppp.myjournylife.file.service

import com.google.firebase.cloud.StorageClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.net.URLEncoder
import java.util.*

@Service
class FileUploadService {

    @Value("\${firebase.bucket-name}")
    lateinit var bucket: String

    fun uploadImageAtFirebase(files: List<MultipartFile>): String{
        val image = files.first()
        val uuid = UUID.randomUUID().toString()
        val filename = URLEncoder.encode("$uuid-${image.originalFilename}", "UTF-8")

        val bucketInstance = StorageClient.getInstance().bucket(bucket)
        println("2")
        val contentStream = ByteArrayInputStream(image.bytes)
        println("3")
        val blob = bucketInstance.create(filename, contentStream, image.contentType)
        println("4")
        return blob.mediaLink
    }
}