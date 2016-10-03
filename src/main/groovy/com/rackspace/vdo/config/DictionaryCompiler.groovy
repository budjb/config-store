package com.rackspace.vdo.config

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
