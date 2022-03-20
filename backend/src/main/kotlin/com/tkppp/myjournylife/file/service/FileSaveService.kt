package com.tkppp.myjournylife.file.service

import com.tkppp.myjournylife.file.domain.File
import com.tkppp.myjournylife.file.domain.FileRepository
import com.tkppp.myjournylife.member.domain.Member
import org.springframework.stereotype.Service

@Service
class FileSaveService(
    private val fileRepository: FileRepository
) {
    fun saveFileDataAtDatabase(images: List<String>, member: Member) {
        for (image in images) {
            fileRepository.save(
                File(
                    filename = image, member = member
                )
            )
        }
    }
}