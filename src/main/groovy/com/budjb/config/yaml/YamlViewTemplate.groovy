package com.budjb.config.yaml

import com.budjb.config.dictionary.ConfigurationTemplateHelper
import grails.views.AbstractWritableScript
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

/**
 * A YAML view template.
 *
 * This implementation is likely not complete for use with Grails views.
 */
abstract class YamlViewTemplate extends AbstractWritableScript {
    /**
     * File extension.
     */
    static final String EXTENSION = 'yml'

    /**
     * YAML data structure builder.
     */
    MapListBuilder builder

    /**
     * YAML dumper options.
     */
    DumperOptions dumperOptions

    /**
     * Configuration template helper.
     */
    ConfigurationTemplateHelper configurationTemplateHelper

    /**
     * Render the script.
     *
     * @param writer The writer
     * @return The original writer or a wrapped version
     */
    @Override
    Writer doWrite(Writer writer) {
        builder = new MapListBuilder()

        dumperOptions = new DumperOptions()
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)

        run()

        Yaml yaml = new Yaml(dumperOptions)
        yaml.dump(builder.data, writer)

        return writer
    }

    /**
     * Creates a YAML data structure based on the given closure.
     *
     * @param closure
     */
    void yaml(@DelegatesTo(MapListBuilder.MapListBuilderDelegate) Closure closure) {
        builder.call closure
    }

    /**
     * Uses the given map as the YAML data structure.
     *
     * @param data
     */
    void yaml(Map<?, ?> data) {
        builder.data = data
    }

    /**
     * Uses the given list as the YAML data structure.
     *
     * @param data
     */
    void yaml(List<?> data) {
        builder.data = data
    }

    /**
     * Returns the configuration template helper.
     *
     * @return
     */
    ConfigurationTemplateHelper getD() {
        return configurationTemplateHelper
    }
}
