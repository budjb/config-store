package com.budjb.config.jaxrs

import groovy.transform.CompileStatic

@CompileStatic
class JsonView {
    /**
     * Name of the view to render.
     */
    String view

    /**
     * View model.
     */
    Map<?, ?> model

    /**
     * Constructor.
     *
     * @param view
     * @param model
     */
    JsonView(String view, Map<?, ?> model) {
        this.view = view
        this.model = model
    }
}
