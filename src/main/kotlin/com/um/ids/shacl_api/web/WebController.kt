package com.um.ids.shacl_api.web

import com.um.ids.shacl_api.service.shacl_service.ShaclServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class WebController {

    @Autowired
    lateinit var shaclService: ShaclServiceImpl

    @PostMapping("/validate",
        consumes = ["multipart/form-data"],
        produces = ["text/turtle"])
    fun validateShacl(
        @RequestParam(required = true) data: MultipartFile,
        @RequestParam(required = true) shacl: MultipartFile,
        @RequestParam(required = false) dataFormat: String?,
        @RequestParam(required = false) shaclFormat: String?
    ): ResponseEntity<String> {
        if (data.isEmpty || shacl.isEmpty) {
            return ResponseEntity("Missing required form fields", HttpStatus.BAD_REQUEST)
        }
        return try {
            val result = shaclService.validateShacl(data.inputStream, shacl.inputStream, dataFormat, shaclFormat)
            ResponseEntity(result, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("Error validating SHACL: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }

}
