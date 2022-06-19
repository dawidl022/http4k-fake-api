package io.github.dawidl022.http4kfakeapi.graphql

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.ExecutionInput.Builder
import graphql.GraphQL.newGraphQL
import io.github.dawidl022.http4kfakeapi.resolvers.*
import org.http4k.graphql.GraphQLHandler
import org.http4k.graphql.GraphQLRequest
import org.http4k.graphql.GraphQLResponse

class ApiGraphQLHandler : GraphQLHandler {
    private val graphQL = newGraphQL(toSchema(
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
    )).build()

    override fun invoke(payload: GraphQLRequest) = GraphQLResponse.from(
        graphQL.execute(
            Builder()
                .query(payload.query)
                .variables(payload.variables)
        )
    )
}
