package com.budjb.config

import com.budjb.config.jaxrs.JsonView
import com.budjb.config.jaxrs.WebResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource

import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path('/dictionaries')
class DictionariesResource {
    @Autowired
    MessageSource messageSource

    @GET
    Response getAllDictionaries() {
        return WebResponse.ok(new JsonView('/dictionary/collection', [dictionaries: Dictionary.getAll()]))
    }

    @POST
    Response createDictionary(Dictionary dictionary) {
        if (!dictionary.validate()) {
            return WebResponse.unprocessableEntity(new JsonView('/errors/validationFailed', [errors: dictionary.getErrors(), domain: Dictionary]))
        }

        dictionary.save()

        return WebResponse.created(new JsonView('/dictionary/show', [dictionary: dictionary]))
    }

    @GET
    @Path('/{name}')
    Response getDictionary(@PathParam('name') String name) {
        Dictionary dictionary = Dictionary.findByName(name)

        if (!dictionary) {
            return WebResponse.notFound(new JsonView('/errors/notFound', [domain: Dictionary, id: name]))
        }

        return WebResponse.ok(new JsonView('/dictionary/show', [dictionary: dictionary]))
    }

    @DELETE
    @Path('/{name}')
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
