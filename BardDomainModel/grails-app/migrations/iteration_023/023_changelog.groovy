package iteration_023

databaseChangeLog = {
    String bardDomainModelMigrationsDir = ctx.migrationResourceAccessor.baseDirectory
    File migrationsDir = new File(bardDomainModelMigrationsDir)
    String BACKSLASH_ONLY_OPTIONAL_WHITESPACE = /(?m)^\s*\/\s*$/

    changeSet(author: "pmontgom", id: "iteration-023/01-populate-qualifier", dbms: 'oracle', context: 'standard') {
        sqlFile(path:  "${migrationsDir}/iteration_023/01-populate-qualifier.sql", stripComments: true)
    }
}

