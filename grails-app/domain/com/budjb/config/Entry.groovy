package com.budjb.config

class Entry {
    String key
    String value

    static belongsTo = [dictionary: Dictionary]

    static constraints = {
        key unique: ['dictionary'], blank: false, nullable: false
        value blank: false, nullable: false
    }
}
