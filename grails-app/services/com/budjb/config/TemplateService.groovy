package com.budjb.config

import grails.transaction.Transactional
import groovy.text.SimpleTemplateEngine
import groovy.text.Template

@Transactional
class TemplateService {
    String renderTemplate(ConfigTemplate configTemplate, Dictionary dictionary) {
        ConfigurationTemplateHelper broker = new ConfigurationTemplateHelper(dictionary)

        SimpleTemplateEngine engine = new SimpleTemplateEngine()
        Template template = engine.createTemplate(configTemplate.getTemplate())
        Writable writable = template.make([g: broker])
        StringWriter writer = new StringWriter()
        writable.writeTo(writer)

        return writer.toString()
    }
}
