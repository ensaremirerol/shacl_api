package com.um.ids.shacl_api.service.shacl_service

import java.io.InputStream


interface ShaclServiceImpl {
    fun validateShacl(
        data: InputStream,
        shacl: InputStream,
        dataFormat: String?,
        shaclFormat: String?
    ): String

}