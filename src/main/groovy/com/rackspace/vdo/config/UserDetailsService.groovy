package com.rackspace.vdo.config

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserDetailsService extends GormUserDetailsService {
    /**
     * Overriding this method since the logic to pull authorities for a user and group now
     * lies within the {@link User} domain.
     *
     * @param user
     * @param username
     * @param loadRoles
     * @return
     */
    protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
        if (!loadRoles) {
            return []
        }

        def conf = SpringSecurityUtils.securityConfig

        String authoritiesPropertyName = conf.userLookup.authoritiesPropertyName
        String authorityPropertyName = conf.authority.nameField

        Collection<?> userAuthorities = user."$authoritiesPropertyName"
        def authorities = userAuthorities.collect { new SimpleGrantedAuthority(it."$authorityPropertyName") }

        return authorities ?: [NO_ROLE]
    }
}
