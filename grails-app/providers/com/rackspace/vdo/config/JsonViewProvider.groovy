package com.rackspace.vdo.config

import com.rackspace.vdo.config.jaxrs.JsonView
import grails.plugin.json.view.JsonViewTemplateEngine
import groovy.text.Template
import org.grails.plugins.jaxrs.provider.MessageBodyWriterSupport
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.Provider

@Provider
@Produces(['application/json'])
class JsonViewProvider extends MessageBodyWriterSupport<JsonView> {
    /**
     * Json view template engine.
     */
    @Autowired
    JsonViewTemplateEngine jsonTemplateEngine

    /**
     * Writes an object of type given by this class type parameter to the
     * response entity output stream.
     *
     * @param jsonView object to be written.
     * @param httpHeaders HTTP response headers.
     * @param entityStream response entity output stream.
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    protected void writeTo(JsonView jsonView, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        Template template = jsonTemplateEngine.resolveTemplate(jsonView.getView())
        Writable writable = template.make(jsonView.getModel())
        OutputStreamWriter writer = new OutputStreamWriter(entityStream)
        writable.writeTo(writer)
        writer.flush()
    }
}
