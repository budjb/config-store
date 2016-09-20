package com.budjb.config

class ConfigurationTemplateHelper {
    /**
     * Root dictionary instance.
     */
    Dictionary root

    /**
     * Cache of dictionary values for all dictionaries in the hierarchy.
     */
    Map<String, Map<String, String>> models = [:]

    /**
     * Compiled dictionary values with proper inheritance observed.
     */
    Map<String, String> compiled = [:]

    /**
     * Constructor.
     *
     * @param root
     */
    ConfigurationTemplateHelper(Dictionary root) {
        this.root = root

        cacheAndCompile(root)
    }

    /**
     * Iterates through all dictionaries and caches their entries. Also compiles
     * the entries based on the dictionary hierarchy.
     *
     * @param dictionary
     */
    void cacheAndCompile(Dictionary dictionary) {
        if (dictionary.parent) {
            cacheAndCompile(dictionary.parent)
        }

        Map<String, String> entries = dictionary.entries.collectEntries {
            [(it.getKey()): it.getValue()]
        }

        models.put(dictionary.name, entries)
        compiled.putAll(entries)
    }

    /**
     * Returns a value identified by the given key from the compiled entry set.
     *
     * @param key
     * @param optional
     * @return
     */
    String get(String key, String defaultValue = null) {
        if (!compiled.containsKey(key)) {
            if (defaultValue != null) {
                return defaultValue
            }
            throw new Exception('uh oh') // TODO: custom exception
        }

        return compiled.get(key)
    }
}
