dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	username = "root"
	password = "genome12"
	dialect = "org.hibernate.dialect.MySQLDialect"
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
	cache.region.factory_class = 'org.hibernate.cache.RegionFactory'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:mysql://sinaa:3306/mlbd"
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://sinaa:3306/mlbd"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://sinaa:3306/mlbd"
		}
	}
}