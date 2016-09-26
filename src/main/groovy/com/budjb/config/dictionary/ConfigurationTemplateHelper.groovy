package com.budjb.config.dictionary

import com.budjb.config.Dictionary

/**
 * The ConfigurationTemplateHelper is intended to provide a simple interface to retrieve
 * {@link Dictionary} values compiled from a hierarchy of {@link Dictionary} instances for
 * use in configuration templates. All configuration templates will be provided
 */
class ConfigurationTemplateHelper {
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

        compiled.putAll(entries)
    }

    /**
     * Returns the value for the given key if it exists in the dictionary.
     *
     * @param key
     * @return
     * @throws MissingConfigurationEntryException
     */
    String get(String key) throws MissingConfigurationEntryException {
        if (!compiled.containsKey(key)) {
            throw new MissingConfigurationEntryException(key)
        }
        return compiled.get(key)
    }

    /**
     * Returns the value for the given key if it exists in the dictionary. If it does not,
     * the given default is returned instead.
     *
     * @param key
     * @param defaultValue
     * @return
     */
    String get(String key, String defaultValue) {
        if (!compiled.containsKey(key)) {
            return defaultValue
        }

        return compiled.get(key)
    }

    /**
     * Returns the value for the given key if it exists in the dictionary. The value is cast
     * to the given type.
     *
     * @param key
     * @param type
     * @return
     */
    public <T> T get(String key, Class<T> type) {
        return get(key).asType(type)
    }

    /**
     * Returns the value for the given key if it exists in the dictionary. If it does not,
     * the give default is returned instead. The value is cast to the given type.
     *
     * @param key
     * @param defaultValue
     * @param type
     * @return
     */
    public <T> T get(String key, T defaultValue, Class<T> type) {
        try {
            return get(key).asType(type)
        }
        catch (MissingConfigurationEntryException ignored) {
            return defaultValue
        }
    }

    /**
     * An exception that gets thrown when a key is requested that isn't defined in the dictionary.
     */
    static class MissingConfigurationEntryException extends RuntimeException {
        /**
         * Name of the entry key.
         */
        String key

        MissingConfigurationEntryException(String key) {
            super()
            this.key = key
        }

        /**
         * Hard-define the exception message.
         *
         * @return
         */
        @Override
        String getMessage() {
            return "no configuration value with key '${key}' is defined with the current dictionary"
        }
    }
}
