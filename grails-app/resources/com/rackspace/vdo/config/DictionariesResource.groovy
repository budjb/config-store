package com.rackspace.vdo.config

import com.rackspace.vdo.config.jaxrs.JsonView
import com.rackspace.vdo.config.jaxrs.WebResponse
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource

import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path('/dictionaries')
class DictionariesResource {
    @Autowired
    DictionaryCompiler dictionaryCompiler

    @Autowired
    MessageSource messageSource

    @GET
    @Secured(['ROLE_VIEW'])
    Response getAllDictionaries() {
        return WebResponse.ok(new JsonView('/dictionary/collection', [dictionaries: Dictionary.getAll()]))
    }

    @POST
    @Secured(['ROLE_MODIFY'])
    Response createDictionary(Dictionary dictionary) {
        if (!dictionary.validate()) {
            return WebResponse.unprocessableEntity(new JsonView('/errors/validationFailed', [errors: dictionary.getErrors(), domain: Dictionary]))
        }

        dictionary.save()

        return WebResponse.created(new JsonView('/dictionary/show', [dictionary: dictionary]))
    }

    @GET
    @Path('/{name}')
    @Secured(['ROLE_VIEW'])
    Response getDictionary(@PathParam('name') String name) {
        Dictionary dictionary = Dictionary.findByName(name)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: name]))
        }

        return WebResponse.ok(new JsonView('/dictionary/show', [dictionary: dictionary]))
    }

    @GET
    @Path('/{name}/compiled')
    @Secured(['ROLE_VIEW'])
    Response compileDictionary(@PathParam('name') String name) {
        Dictionary dictionary = Dictionary.findByName(name)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: name]))
        }

        return WebResponse.ok(new JsonView('/dictionary/compiled', [dictionary: dictionary, values: dictionaryCompiler.compile(dictionary)]))
    }

    @DELETE
    @Path('/{name}')
    @Secured(['ROLE_MODIFY'])
    Response deleteDictionary(@PathParam('name') String name) {
        Dictionary dictionary = Dictionary.findByName(name)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: name]))
        }

        dictionary.delete()

        return WebResponse.noContent()
    }

    @GET
    @Path('/{name}/entries')
    @Secured(['ROLE_VIEW'])
    Response getDictionaryEntries(@PathParam('name') String name) {
        Dictionary dictionary = Dictionary.findByName(name)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: name]))
        }

        Set<Entry> entries = dictionary.getEntries() ?: []

        return WebResponse.ok(new JsonView('/dictionary/entry/collection.gson', [entries: entries]))
    }

    @GET
    @Path('/{name}/entries/{key}')
    @Secured(['ROLE_VIEW'])
    Response getEntry(@PathParam('name') String dictionaryName, @PathParam('key') String keyName) {
        Dictionary dict = Dictionary.findByName(dictionaryName)

        if (!dict) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: dictionaryName]))
        }

        Entry entry = Entry.find {
            key == keyName && dictionary.name == dictionaryName
        }

        if (!entry) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Entry, id: keyName]))
        }

        return WebResponse.ok(new JsonView('/dictionary/entry/show.gson', [entry: entry]))
    }

    @PUT
    @Path('/{name}/entries/{key}')
    @Consumes(['text/plain'])
    @Secured(['ROLE_MODIFY'])
    Response addEntry(@PathParam('name') String dictionaryName, @PathParam('key') String keyName, String value) {
        Dictionary dict = Dictionary.findByName(dictionaryName)

        if (!dict) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: dictionaryName]))
        }

        Entry entry = Entry.find {
            key == keyName && dictionary.name == dictionaryName
        }

        if (!entry) {
            entry = new Entry(key: keyName, value: value)
            dict.addToEntries(entry)
            dict.save()
        }
        else {
            entry.value = value
            entry.save()
        }

        return WebResponse.ok(new JsonView('/dictionary/entry/show.gson', [entry: entry]))
    }

    @DELETE
    @Path('/{name}/entries/{key}')
    @Secured(['ROLE_MODIFY'])
    Response deleteEntry(@PathParam('name') String dictionaryName, @PathParam('key') String keyName) {
        Dictionary dict = Dictionary.findByName(dictionaryName)

        if (!dict) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: dictionaryName]))
        }

        Entry entry = Entry.find {
            key == keyName && dictionary.name == dictionaryName
        }

        if (!entry) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Entry, id: keyName]))
        }

        dict.removeFromEntries(entry)
        entry.delete()

        return WebResponse.noContent()
    }
}
