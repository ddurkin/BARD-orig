package iteration_025

databaseChangeLog = {
    String bardDomainModelMigrationsDir = ctx.migrationResourceAccessor.baseDirectory
    File migrationsDir = new File(bardDomainModelMigrationsDir)


    changeSet(author: 'jasiedu', id: 'add trigger to recompute assay short name on assayType update', dbms: 'oracle', context: 'standard') {
        grailsChange {
            change {
                sql.execute("""CREATE OR REPLACE TRIGGER TR_UPD_ASSAY_TYPE
      AFTER UPDATE OF ASSAY_TYPE ON ASSAY
      FOR EACH ROW BEGIN
      MANAGE_NAMES.UPDATE_ASSAY_SHORT_NAME(:OLD.ASSAY_ID);
END;
""")
            }
        }
    }
    changeSet(author: 'jasiedu', id: 'drop trigger to recompute assay short name on assayType update', dbms: 'oracle', context: 'standard') {
        grailsChange {
            change {
                sql.execute("""DROP TRIGGER TR_UPD_ASSAY_TYPE""")
            }
        }
    }
    changeSet(author: "jasiedu", id: "iteration-025/01-recreate-context-item-constraints", dbms: "oracle", context: "standard") {
        sqlFile(path: "${migrationsDir}/iteration_025/01-recreate-context-item-constraints.sql", stripComments: true)
    }
    changeSet(author: "jasiedu", id: "iteration-025/02-rename-comments-column-element", dbms: "oracle", context: "standard") {
        sqlFile(path: "${migrationsDir}/iteration_025/02-rename-comments-column-element.sql", stripComments: true)
    }
}

