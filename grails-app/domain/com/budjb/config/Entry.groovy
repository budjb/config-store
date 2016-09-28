package com.budjb.config

import javax.persistence.Transient

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
     * Encryption service.
     */
    @Transient
    EncryptionService encryptionService

    /**
     * Field constraints.
     */
    static constraints = {
        key unique: ['dictionary'], blank: false, nullable: false
        value blank: false, nullable: false
        dateCreated nullable: true
        lastUpdated nullable: true
    }

    /**
     * Decrypts the value of the entry after it is loaded from the database.
     *
     * @return
     */
    def afterLoad() {
        value = encryptionService.decrypt(value)
    }

    /**
     * Encrypts the value of the entry before it is inserted into the database.
     *
     * @return
     */
    def beforeInsert() {
        value = encryptionService.encrypt(value)
    }

    /**
     * Encrypts the value of the entry before it is updated in the database.
     *
     * @return
     */
    def beforeUpdate() {
        value = encryptionService.encrypt(value)
    }
}
