package io.github.dawidl022.http4kfakeapi.db

import io.github.dawidl022.http4kfakeapi.models.Albums
import io.github.dawidl022.http4kfakeapi.models.util.SeedableTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            "jdbc:${System.getenv("FAKE_DATABASE_URL")}", driver = System.getenv("DB_DRIVER"))

        transaction {
            SchemaUtils.create(Albums)
//            SchemaUtils.create(Photos)
//            SchemaUtils.create(Todos)
        }
        seedDb(Albums)
//        seedDb(Photos)
//        seedDb(Todos)
    }

    private fun <T : SeedableTable<M>, M> seedDb(table: T) {
        if (transaction {
                table.selectAll().count() == 0L
            }) {
            transaction {
                table.batchInsert(table.seed()) {
                    table.builderSchema(this, it)
                }
            }
        }
    }
}
