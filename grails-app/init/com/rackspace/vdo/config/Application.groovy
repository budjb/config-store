package com.rackspace.vdo.config

import com.rackspace.vdo.stdlib.VdoApplication
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import grails.plugin.json.view.JsonViewConfiguration
import grails.plugin.json.view.JsonViewTemplateEngine
import grails.util.Holders
import org.springframework.context.annotation.Bean
import org.springframework.security.web.context.NullSecurityContextRepository

class Application extends GrailsAutoConfiguration implements VdoApplication {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Override
    Closure doWithSpring() {{ ->
        'jsonTemplateEngine'(JsonViewTemplateEngine, new JsonViewConfiguration())
        'securityContextRepository'(NullSecurityContextRepository)
        'encryptionUtil'(EncryptionUtil) {
            password = Holders.config.crypto.password
        }
    }}
}