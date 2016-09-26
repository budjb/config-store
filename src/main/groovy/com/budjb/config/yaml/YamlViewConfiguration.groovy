package com.budjb.config.yaml

import grails.views.GenericViewConfiguration

/**
 * YAML view template engine configuration.
 */
class YamlViewConfiguration implements GenericViewConfiguration {
    /**
     * Module name.
     */
    static final String MODULE_NAME = "yaml"

    /**
     * Constructor that sets the configuration up for YAML.
     */
    YamlViewConfiguration() {
        setExtension(YamlViewTemplate.EXTENSION)
        setCompileStatic(true)
        setBaseTemplateClass(YamlViewTemplate)
    }

    /**
     * Returns the view module's name.
     *
     * @return The name of the views module (example json or markup)
     */
    @Override
    String getViewModuleName() {
        return MODULE_NAME
    }
}
