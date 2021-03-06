package io.github.dawidl022.http4kfakeapi.resolvers

import io.github.dawidl022.http4kfakeapi.models.Todo
import io.github.dawidl022.http4kfakeapi.models.Todos


class TodoMutation {
    fun createTodo(todo: Todo): Todo =
        Todos.create(todo)

    fun updateTodo(id: Int, todo: Todo): Todo =
        Todos.put(id, todo)

    fun deleteTodo(id: Int): Todo =
        Todos.delete(id)
}
