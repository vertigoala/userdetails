dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "root"
    password = ""
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
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/emmet"
            username = "root"
            password = "password"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/emmet"
            username = "emmet"
            password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/emmet"
            username = "emmet"
            password = "Kllhj789879978Gkjh"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
        }
    }
}
