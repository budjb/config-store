package com.rackspace.vdo.config

class BootStrap {
    def init = { servletContext ->
        if (!Role.findByAuthority('ROLE_MODIFY')) {
            new Role(authority: 'ROLE_MODIFY').save()
        }
        if (!Role.findByAuthority('ROLE_VIEW')) {
            new Role(authority: 'ROLE_VIEW').save()
        }
    }
}
