dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = ""
    password = ""
    properties {
        // http://www.grails.org/doc/latest/guide/single.html#dataSource
        // see http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#Common_Attributes for more
        timeBetweenEvictionRunsMillis = 60000 // milliseconds (default = 5000)
        testOnBorrow = true // default = false
        testOnReturn = false // default = false
        testWhileIdle = true // default = false
        validationQuery = "SELECT 1" // default = null
        validationQueryTimeout = 10 //seconds (default = -1 i.e. disabled)
        validationInterval = 30000 // milliseconds, default is 30000 (30 seconds)
        removeAbandoned = true // // default = false
        removeAbandonedTimeout = 300 // seconds (default = 60)
        logAbandoned = true // adds some overhead to every borrow from the pool, disable if it becomes a performance issue
        logValidationErrors = true // default = false, logs validation errors at SEVERE

        // NOTE: removeAbandoned = true OR testWhileIdle = true enables the Pool Cleaner. There is a bug with the
        // pool cleaner which causes deadlocks when using older mysql jdbc drivers. This configuration has been
        // tested successfully with mysql:mysql-connector-java:5.1.34.

        // mysql jdbc connection properties - see http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html
        dbProperties {
            autoReconnect = true
            connectTimeout = 0
            useUnicode = true
            characterEncoding = "UTF-8"
        }
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    hibernate.show_sql = false
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "none"
            url = "jdbc:mysql://localhost/emmet"
            username = "root"
            password = "password"
        }
    }
    production {
        dataSource {
            dbCreate = "none"
        }
    }
}