package com.tkppp.myjournylife.file.controller

import com.tkppp.myjournylife.file.service.FileUploadService
import com.tkppp.myjournylife.file.util.ImageType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class FileUploadController(
    private val fileUploadService: FileUploadService
) {

    @PostMapping("/image/review")
    @Secured("MEMBER")
    fun uploadContentImageFile(@RequestParam imageFiles: List<MultipartFile>): ResponseEntity<List<HashMap<String, String>>> {
        val imageData = fileUploadService.uploadImageAtAwsS3(imageFiles, ImageType.CONTENT)
        return ResponseEntity(imageData, HttpStatus.OK)
    }
}