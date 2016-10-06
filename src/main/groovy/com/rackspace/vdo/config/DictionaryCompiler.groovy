package com.rackspace.vdo.config

/**
 * A utility class responsible for compiling a {@link Map} of key/value pairs sourced
 * from a {@link Dictionary}. {@link Dictionary#parent} links are followed and key/value
 * pairs are inherited and overridden as necessary.
 */
class DictionaryCompiler {
    /**
     * Compiles a dictionary with all of its parent dictionary's values inherited.
     *
     * @param dictionary
     * @return
     */
    Map compile(Dictionary dictionary) {
        if (!dictionary) {
            return [:]
        }

        Map compiled = compile(dictionary.parent)

        dictionary.entries?.each {
            compiled.put(it.key, it.value)
        }

        return compiled
    }
}
