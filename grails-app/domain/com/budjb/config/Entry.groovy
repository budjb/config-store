package com.budjb.config

/**
 * Represents a key/value pair stored in a {@link Dictionary}.
 */
class Entry {
    /**
     * Name of the entry.
     */
    String key

    /**
     * Value of the entry.
     */
    String value

    /**
     * Date the config template was created.
     */
    Date dateCreated

    /**
     * Date the config template was last updated.
     */
    Date lastUpdated

    /**
     * Parent relationship.
     */
    static belongsTo = [dictionary: Dictionary]

    /**
     * Field constraints.
     */
    static constraints = {
        key unique: ['dictionary'], blank: false, nullable: false
        value blank: false, nullable: false
        dateCreated nullable: true
        lastUpdated nullable: true
    }
}
