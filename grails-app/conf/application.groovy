grails.plugin.springsecurity.userDetailsServiceBeanClass = 'com.rackspace.vdo.config.UserDetailsService'
grails.plugin.springsecurity.globalAuth.allowAuthToken = true
grails.plugin.springsecurity.providerNames = [
    'anonymousAuthenticationProvider',
    'globalAuthProvider'
]
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.rackspace.vdo.config.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.rackspace.vdo.config.UserRole'
grails.plugin.springsecurity.userLookup.groupJoinClassName = 'com.rackspace.vdo.config.UserGroup'
grails.plugin.springsecurity.groups.groupDomainClassName = 'com.rackspace.vdo.config.Group'
grails.plugin.springsecurity.groups.groupNamePropertyName = 'name'
grails.plugin.springsecurity.authority.className = 'com.rackspace.vdo.config.Role'
grails.plugin.springsecurity.authority.groupAuthorityNameField = 'authorities'
grails.plugin.springsecurity.useRoleGroups = true
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/', access: ['permitAll']],
    [pattern: '/error', access: ['permitAll']],
    [pattern: '/index', access: ['permitAll']],
    [pattern: '/index.gsp', access: ['permitAll']],
    [pattern: '/shutdown', access: ['permitAll']],
    [pattern: '/assets/**', access: ['permitAll']],
    [pattern: '/**/js/**', access: ['permitAll']],
    [pattern: '/**/css/**', access: ['permitAll']],
    [pattern: '/**/images/**', access: ['permitAll']],
    [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
    [pattern: '/assets/**', filters: 'none'],
    [pattern: '/**/js/**', filters: 'none'],
    [pattern: '/**/css/**', filters: 'none'],
    [pattern: '/**/images/**', filters: 'none'],
    [pattern: '/**/favicon.ico', filters: 'none'],
    [pattern: '/**', filters: 'JOINED_FILTERS']
]
grails.plugin.springsecurity.roleHierarchy = '''
ROLE_MODIFY > ROLE_VIEW
'''
