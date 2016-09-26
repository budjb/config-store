package com.budjb.config

import com.budjb.config.dictionary.ConfigurationTemplateHelper
import com.budjb.config.yaml.YamlViewTemplate
import com.budjb.config.yaml.YamlViewTemplateEngine
import groovy.text.Template

class TemplateService {
    /**
     * Renders a given template with the configuration values provided by the given dictionary.
     *
     * @param configTemplate
     * @param dictionary
     * @return
     */
    String renderTemplate(ConfigTemplate configTemplate, Dictionary dictionary) {
        YamlViewTemplateEngine engine = new YamlViewTemplateEngine()
        Template template = engine.createTemplate(configTemplate.getTemplate())
        YamlViewTemplate writable = (YamlViewTemplate) template.make()
        writable.setConfigurationTemplateHelper(new ConfigurationTemplateHelper(dictionary))
        StringWriter writer = new StringWriter()
        writable.writeTo(writer)
        return writer.toString()
    }
}
