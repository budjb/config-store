import grails.util.Environment

/*
 * Besides the standard configuration options, the following keys are required to compile this
 * configuration file:
 *
 * - config-store.crypto.password
 */
yaml {
    if (Environment.isDevelopmentMode()) {
        dataSource localMemDb()
    }
    else {
        dataSource mysqlDb(d.get('database.url'), d.get('database.username'), d.get('database.password'))
    }

    vdo {
        clients {
            globalAuth {
                v2 {
                    enabled = true
                    baseAddress = d.get('identity.url')
                    adminIdentifier = 'admin'

                    accounts = [
                        [
                            username  : d.get('identity.accounts.vdo.username'),
                            password  : d.get('identity.accounts.vdo.password'),
                            identifier: 'admin',
                            type      : 'system',
                            isDefault : true
                        ]
                    ]
                }
            }
        }
    }

    crypto {
        password d.get('config-store.crypto.password')
    }
}
