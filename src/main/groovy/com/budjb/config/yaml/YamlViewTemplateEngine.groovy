package com.budjb.config.yaml

import grails.views.ResolvableGroovyTemplateEngine

class YamlViewTemplateEngine extends ResolvableGroovyTemplateEngine {
    /**
     * Constructor.
     */
    YamlViewTemplateEngine() {
        this(new YamlViewConfiguration())
    }

    /**
     * Creates a ResolvableGroovyTemplateEngine for the given base class name and file extension
     *
     * @param configuration
     */
    YamlViewTemplateEngine(YamlViewConfiguration configuration) {
        super(configuration)
    }

    @Override
    String getDynamicTemplatePrefix() {
        return 'YamlView'.intern()
    }
}
