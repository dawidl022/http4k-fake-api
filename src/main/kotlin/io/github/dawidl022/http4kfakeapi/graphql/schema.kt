package io.github.dawidl022.http4kfakeapi.graphql

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.ExecutionInput.Builder
import graphql.GraphQL.newGraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaPrinter
import io.github.dawidl022.http4kfakeapi.Config
import io.github.dawidl022.http4kfakeapi.resolvers.*
import org.http4k.graphql.GraphQLHandler
import org.http4k.graphql.GraphQLRequest
import org.http4k.graphql.GraphQLResponse
import java.io.File

class ApiGraphQLHandler : GraphQLHandler {
    private val schema = toSchema(
        config = SchemaGeneratorConfig(supportedPackages = listOf("io.github.dawidl022.http4kfakeapi.models")),
        queries = listOf(
            AlbumQuery(),
            PhotoQuery(),
            TodoQuery(),
        ).map(::TopLevelObject),
        mutations = listOf(
            AlbumMutation(),
            PhotoMutation(),
            TodoMutation(),
        ).map(::TopLevelObject)
    )
    init {
        saveGeneratedSchemaToFile(schema)
    }
    private val graphQL = newGraphQL(schema).build()

    override fun invoke(payload: GraphQLRequest) = GraphQLResponse.from(
        graphQL.execute(
            Builder()
                .query(payload.query)
                .variables(payload.variables)
        )
    )
}

/** Only save to file if schemaDir is not null in Config */
fun saveGeneratedSchemaToFile(schema: GraphQLSchema) {
    Config.schemaDir?.let { File(it + "generated.graphql").writeText(SchemaPrinter().print(schema)) }
}
