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
}
