package iteration_031

databaseChangeLog = {

    changeSet(author: "ddurkin (generated)", id: "1376496330012-1", context: "standard") {
        createTable(tableName: "quantity") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "quantityPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "shopping_cart_id", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "shopping_item_id", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "value", type: "number(10,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-2", context: "standard") {
        createTable(tableName: "shopcartintertestprod") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shopcartinterPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "shopping_item_id", type: "number(19,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-3", context: "standard") {
        createTable(tableName: "shoppable") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shoppablePK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "shopping_item_id", type: "number(19,0)")

            column(name: "class", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar2(255 char)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-4", context: "standard") {
        createTable(tableName: "shopping_cart") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shopping_cartPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "checked_out", type: "number(1,0)") {
                constraints(nullable: "false")
            }

            column(name: "lasturl", type: "varchar2(255 char)")

            column(name: "sessionid", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-5", context: "standard") {
        createTable(tableName: "shopping_cart_shopping_item") {
            column(name: "shopping_cart_items_id", type: "number(19,0)")

            column(name: "shopping_item_id", type: "number(19,0)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-6", context: "standard") {
        createTable(tableName: "shopping_item") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shopping_itemPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-13", context: "standard") {
        createSequence(sequenceName: "hibernate_sequence")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-7", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_cart_id", baseTableName: "quantity", constraintName: "FKB368648BCC86F46F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_cart", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-8", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_item_id", baseTableName: "quantity", constraintName: "FKB368648B29A10B8F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_item", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-9", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_item_id", baseTableName: "shopcartintertestprod", constraintName: "FKB22C606F29A10B8F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_item", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-10", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_item_id", baseTableName: "shoppable", constraintName: "FK83826C9429A10B8F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_item", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-11", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_cart_items_id", baseTableName: "shopping_cart_shopping_item", constraintName: "FKD02304E254937B4E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_cart", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1376496330012-12", context: "standard") {
        addForeignKeyConstraint(baseColumnNames: "shopping_item_id", baseTableName: "shopping_cart_shopping_item", constraintName: "FKD02304E229A10B8F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shopping_item", referencesUniqueColumn: "false")
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-4") {
        createTable(tableName: "hill_curve_value_holder") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "hill_curve_vaPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "coef", type: "double precision")

            column(name: "identifier", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "qualifier", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "s0", type: "double precision")

            column(name: "s_inf", type: "double precision")

            column(name: "slope", type: "double precision")

            column(name: "spreadActStore", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "sub_column_index", type: "number(10,0)") {
                constraints(nullable: "false")
            }

            column(name: "x_axis_label", type: "varchar2(255 char)")

            column(name: "y_axis_label", type: "varchar2(255 char)")

            column(name: "holderList_idx", type: "number(10,0)")
        }
    }




    changeSet(author: "ddurkin (generated)", id: "1377120073229-7",context: "standard") {
        createTable(tableName: "mcolnorm") {
            column(name: "map_columns_normalization", type: "number(19,0)")

            column(name: "colsToNorm_idx", type: "number(10,0)")

            column(name: "colsToNorm", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-8",context: "standard") {
        createTable(tableName: "mcolsassayname") {
            column(name: "map_columns_to_assay_name", type: "number(19,0)")

            column(name: "colsToAssayName_idx", type: "number(10,0)")

            column(name: "colsToAssayName", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-9",context: "standard") {
        createTable(tableName: "mexptocap") {
            column(name: "exp_id", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "expToCapAssay_idx", type: "number(10,0)")

            column(name: "maptoexp", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-10",context: "standard") {
        createTable(tableName: "mol_spread_sheet_cell") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mol_spread_shPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "activity", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "int_internal_value", type: "number(10,0)") {
                constraints(nullable: "false")
            }

            column(name: "mol_spread_sheet_cell_type", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "molData", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "str_internal_value", type: "varchar2(255 char)")

            column(name: "supplemental_internal_value", type: "varchar2(255 char)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-11",context: "standard") {
        createTable(tableName: "mol_spread_sheet_column_header") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mol_spread_sh_ch_PK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-12",context: "standard") {
        createTable(tableName: "MolSSColSubHeader") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MolSSColSubHePK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "column_title", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "maxResponse", type: "double precision") {
                constraints(nullable: "false")
            }

            column(name: "minResponse", type: "double precision") {
                constraints(nullable: "false")
            }

            column(name: "units_in_column", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }

            column(name: "unitsInColumnAreUniform", type: "number(1,0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-13",context: "standard") {
        createTable(tableName: "MSData") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "MSDataPK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "mol_spreadsheet_derived_method", type: "varchar2(255 char)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-14",context: "standard") {
        createTable(tableName: "msdata_column_pointer") {
            column(name: "columnPter", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "column_pointer_idx", type: "varchar2(255 char)")

            column(name: "column_pointer_elt", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-15",context: "standard") {
        createTable(tableName: "msdata_map_columns_to_assay") {
            column(name: "colsToAssay", type: "number(19,0)")

            column(name: "map_columns_to_assay_idx", type: "varchar2(255 char)")

            column(name: "map_columns_to_assay_elt", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-16",context: "standard") {
        createTable(tableName: "msdata_mss_data") {
            column(name: "mss_data", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "mss_data_idx", type: "varchar2(255 char)")

            column(name: "mss_data_elt", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-17",context: "standard") {
        createTable(tableName: "msdata_row_pointer") {
            column(name: "rowPter", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "row_pointer_idx", type: "varchar2(255 char)")

            column(name: "row_pointer_elt", type: "varchar2(255 char)") {
                constraints(nullable: "false")
            }
        }
    }



    changeSet(author: "ddurkin (generated)", id: "1377120073229-19",context: "standard") {
        createTable(tableName: "spread_sheet_activity_storage") {
            column(name: "id", type: "number(19,0)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "spread_sheet_PK")
            }

            column(name: "version", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "activity_outcome", type: "varchar2(255 char)")

            column(name: "cid", type: "number(19,0)")

            column(name: "dictionary_description", type: "varchar2(255 char)")

            column(name: "dictionary_id", type: "number(10,0)") {
                constraints(nullable: "false")
            }

            column(name: "dictionary_label", type: "varchar2(255 char)")

            column(name: "eid", type: "number(19,0)")

            column(name: "molCell", type: "number(19,0)") {
                constraints(nullable: "false")
            }

            column(name: "potency", type: "double precision")

            column(name: "response_unit", type: "varchar2(255 char)")

            column(name: "sid", type: "number(19,0)")
        }
    }





    changeSet(author: "ddurkin (generated)", id: "1377120073229-22",context: "standard") {
        addColumn(tableName: "shoppable") {
            column(name: "external_id", type: "number(19,0)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-23",context: "standard") {
        addColumn(tableName: "shoppable") {
            column(name: "num_assay_active", type: "number(10,0)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-24",context: "standard") {
        addColumn(tableName: "shoppable") {
            column(name: "num_assay_tested", type: "number(10,0)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-25",context: "standard") {
        addColumn(tableName: "shoppable") {
            column(name: "query_item_type", type: "varchar2(255 char)")
        }
    }

    changeSet(author: "ddurkin (generated)", id: "1377120073229-26",context: "standard") {
        addColumn(tableName: "shoppable") {
            column(name: "smiles", type: "varchar2(255 char)")
        }
    }

}
