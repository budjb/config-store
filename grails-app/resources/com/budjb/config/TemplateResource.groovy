package com.budjb.config

import com.budjb.config.jaxrs.WebResponse
import com.budjb.config.view.writer.JsonView

import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path('/templates')
@Produces(['application/json'])
class TemplateResource {
    @GET
    Response getAllTemplates() {
        return WebResponse.ok(new JsonView('/configTemplate/collection', [configTemplates: ConfigTemplate.getAll()]))
    }

    @POST
    @Consumes(['application/json'])
    Response createTemplate(ConfigTemplate configTemplate) {
        if (!configTemplate.validate()) {
            return WebResponse.unprocessableEntity(new JsonView('/errors/validationFailed', [errors: configTemplate.getErrors(), domain: ConfigTemplate]))
        }

        configTemplate.save()

        return WebResponse.created(new JsonView('/configTemplate/show', [configTemplate: configTemplate]))
    }

    @GET
    @Path('/{name}')
    Response getTemplate(@PathParam('name') String name) {
        ConfigTemplate template = ConfigTemplate.findByName(name)

        if (!template) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: ConfigTemplate, id: name]))
        }

        return WebResponse.ok(new JsonView('/configTemplate/show', [configTemplate: template]))
    }

    @DELETE
    @Path('/{name}')
    Response deleteTemplate(@PathParam('name') String name) {
        ConfigTemplate template = ConfigTemplate.findByName(name)

        if (!template) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: ConfigTemplate, id: name]))
        }

        template.delete()

        return WebResponse.noContent()
    }

    @GET
    @Path('/{name}/contents')
    @Produces(['text/plain'])
    Response getTemplateContents(@PathParam('name') String name) {
        ConfigTemplate template = ConfigTemplate.findByName(name)

        if (!template) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: ConfigTemplate, id: name]))
        }

        return WebResponse.ok(template.getTemplate())
    }

    @PUT
    @Path('/{name}/contents')
    @Produces(['text/plain'])
    @Consumes(['text/plain'])
    Response updateTemplateContents(@PathParam('name') String name, String contents) {
        ConfigTemplate template = ConfigTemplate.findByName(name)

        if (!template) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [clazz: 'ConfigTemplate', id: name]))
        }

        template.setTemplate(contents)
        template.save()

        return WebResponse.ok(template.getTemplate())
    }
}
