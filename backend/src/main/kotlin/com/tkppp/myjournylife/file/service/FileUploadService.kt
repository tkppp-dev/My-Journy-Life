package com.tkppp.myjournylife.file.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.tkppp.myjournylife.error_code.ErrorCode
import com.tkppp.myjournylife.file.util.ImageType
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap

@Service
class FileUploadService(
    private val amazonS3Client: AmazonS3Client
) {

    @Value("\${cloud.aws.s3.bucket-name}")
    lateinit var bucket: String

    @Value("\${cloud.aws.s3.access-url}")
    lateinit var accessUrl: String

    fun uploadImageAtAwsS3(files: List<MultipartFile>, imageType: ImageType): List<HashMap<String, String>> {
        val fileList = mutableListOf<HashMap<String, String>>()
        for (image in files) {
            val bucketDir = "$bucket/images"
            val filename = createFilename(image.originalFilename!!, imageType.identifierName)
            val objectMetadata = ObjectMetadata()

            objectMetadata.contentLength = image.size
            objectMetadata.contentType = image.contentType

            try {
                val inputStream = image.inputStream
                amazonS3Client.putObject(
                    PutObjectRequest(
                        bucketDir, filename, inputStream, objectMetadata
                    )
                )
                fileList.add(
                    hashMapOf(
                        "url" to "${accessUrl}/${filename}",
                        "filename" to filename
                    )
                )
            } catch (ex: IOException) {
                throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.FILE_UPLOAD_FAIL.message,
                    ex
                )
            }
        }
        return fileList
    }

    fun createFilename(originalFilename: String, identifierName: String): String {
        val uuid = UUID.randomUUID().toString()
        val ext = getFileExt(originalFilename)
        return "$identifierName-$uuid.$ext"
    }

    fun getFileExt(filename: String): String {
        return try {
            filename.substring(filename.lastIndexOf('.') + 1)
        } catch (ex: Exception) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_FILE_EXTENSION.name,
                ex
            )
        }
    }
}