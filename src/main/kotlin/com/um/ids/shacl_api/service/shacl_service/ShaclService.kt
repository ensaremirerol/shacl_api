package com.um.ids.shacl_api.service.shacl_service


import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Resource
import org.apache.jena.riot.Lang
import org.apache.jena.riot.RDFWriter
import org.springframework.stereotype.Service
import org.topbraid.shacl.validation.ValidationEngineConfiguration
import org.topbraid.shacl.validation.ValidationUtil
import java.io.InputStream

@Service
class ShaclService: ShaclServiceImpl {
    override fun validateShacl(data: InputStream, shacl: InputStream, dataFormat: String?, shaclFormat: String?): String {
        val dataGraph: Model = ModelFactory.createDefaultModel().read(data, null, dataFormat)
        val shaclGraph: Model = ModelFactory.createDefaultModel().read(shacl, null, shaclFormat)

        val validationEngineConfiguration = ValidationEngineConfiguration()

        validationEngineConfiguration.setValidationErrorBatch(-1)

        val shaclValidationReport: Resource = ValidationUtil.validateModel(dataGraph, shaclGraph, validationEngineConfiguration)

        val reportGraph: Model = shaclValidationReport.model
        // Save the report to a variable
        return RDFWriter.source(reportGraph).lang(Lang.TURTLE).asString()
    }
}