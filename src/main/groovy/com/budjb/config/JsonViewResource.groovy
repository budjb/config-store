package com.budjb.config

import com.budjb.config.writer.JsonView

class JsonViewResource {
    JsonView jsonView(String viewPath, Map<?, ?> model) {
        return new JsonView(viewPath, model)
    }
}
