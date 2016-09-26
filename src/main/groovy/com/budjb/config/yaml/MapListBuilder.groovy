package com.budjb.config.yaml

/**
 * Class that builds a data structure based on lists and maps.
 */
class MapListBuilder {
    /**
     * Data model.
     */
    Object data = [:]

    /**
     * Create a root data structure with the given closure.
     *
     * @param c
     */
    void call(@DelegatesTo(MapListBuilderDelegate) Closure c) {
        if (!data) {
            data = [:]
        }

        call(c, (Map<?, ?>) data)
    }

    /**
     * Create a data structure with the given closure using the given map as the root.
     *
     * @param c
     * @param root
     */
    void call(@DelegatesTo(MapListBuilderDelegate) Closure c, Map<?, ?> root) {
        c = (Closure) c.clone()
        c.delegate = new MapListBuilderDelegate((Map) root)
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.call()
    }

    /**
     * Builder closure delegate.
     */
    class MapListBuilderDelegate {
        /**
         * Root map to add new values to. This will likely not be the absolute
         * root of the data structure.
         */
        private Map root

        /**
         * Constructor.
         *
         * @param root
         */
        MapListBuilderDelegate(Map root) {
            this.root = root
        }

        /**
         * Enable arbitrary method calls to facilitate the DSL builder structure.
         *
         * @param name
         * @param args
         * @return
         */
        @Override
        Object invokeMethod(String name, Object args) {
            if (args != null && Object[].class.isAssignableFrom(args.getClass())) {
                args = (Object[]) args
                if (args.size() != 1) {
                    throw new IllegalArgumentException("only expecting one argument but encountered ${args.size()}")
                }
                Object arg = args[0]

                if (arg instanceof Closure) {
                    Map<?, ?> newMap = [:]
                    this.root.put(name, newMap)
                    call(arg, newMap)
                }
                else {
                    this.root.put(name, arg)
                }
            }

            return null
        }
    }
}
