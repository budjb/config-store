package com.budjb.config

/**
 * A domain that implements a dictionary with key/value pairs. A dictionary
 * can be used as the model to render a {@link ConfigTemplate}.
 */
class Dictionary {
    /**
     * Name of the dictionary.
     */
    String name

    /**
     * Date the config template was created.
     */
    Date dateCreated

    /**
     * Date the config template was last updated.
     */
    Date lastUpdated

    /**
     * Child relationships.
     */
    static hasMany = [children: Dictionary, entries: Entry]

    /**
     * Parent relationship.
     */
    static belongsTo = [parent: Dictionary]

    /**
     * Field constraints.
     */
    static constraints = {
        name unique: true, nullable: false, blank: false
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
