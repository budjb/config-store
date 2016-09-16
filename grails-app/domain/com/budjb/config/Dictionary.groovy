package com.budjb.config

import grails.rest.Resource

class Dictionary {
    /**
     * Name of the dictionary.
     */
    String name

    /**
     * Child relationships.
     */
    static hasMany = [children: Dictionary, entries: Entry]

    /**
     * Parent relationship.
     */
    static belongsTo = [parent: Dictionary]

    /**
     * Sets the name of the dictionary as lower case.
     *
     * @param name
     */
    void setName(String name) {
        this.name = name.toLowerCase()
    }

    /**
     * Field constraints.
     */
    static constraints = {
        name unique: true, nullable: false, blank: false
    }
}
