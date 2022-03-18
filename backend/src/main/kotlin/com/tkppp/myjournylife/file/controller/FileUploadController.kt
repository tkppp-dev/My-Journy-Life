package com.tkppp.myjournylife.file.controller

import com.tkppp.myjournylife.file.service.FileUploadService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun uploadImageFile(@RequestParam imageFiles: List<MultipartFile>): ResponseEntity<String> {
        val imageLink = fileUploadService.uploadImageAtFirebase(imageFiles)
        return ResponseEntity(imageLink, HttpStatus.OK)
    }
}