package com.budjb.config

import com.budjb.config.jaxrs.WebResponse
import com.budjb.config.jaxrs.JsonView

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path('/configs')
class ConfigResource {
    TemplateService templateService

    @GET
    @Path('/template/{templateName}/dictionary/{dictionaryName}')
    Response getRenderedTemplate(@PathParam('templateName') String templateName, @PathParam('dictionaryName') String dictionaryName) {
        Dictionary dictionary = Dictionary.findByName(dictionaryName)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: dictionaryName]), MediaType.APPLICATION_JSON_TYPE)
        }

        ConfigTemplate template = ConfigTemplate.findByName(templateName)

        if (!template) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: ConfigTemplate, id: templateName]), MediaType.APPLICATION_JSON_TYPE)
        }

        return WebResponse.ok(templateService.renderTemplate(template, dictionary), MediaType.TEXT_PLAIN_TYPE)
    }
}
