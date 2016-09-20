package com.budjb.config

/**
 * A domain representing a named configuration template.
 */
class ConfigTemplate {
    /**
     * Name of the configuration template.
     */
    String name

    /**
     * Text template of the configuration.
     */
    String template

    /**
     * Date the config template was created.
     */
    Date dateCreated

    /**
     * Date the config template was last updated.
     */
    Date lastUpdated

    /**
     * Field constraints.
     */
    static constraints = {
        name unique: true, blank: false, nullable: false
        template blank: false, nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
    }

    /**
     * Table mapping.
     */
    static mapping = {
        template type: 'text'
    }
}
