package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem

interface ToDoRepository {
    List<ToDoItem> findAll()

    ToDoItem findById(Long id)

    Long insert(ToDoItem toDoItem)

    def update(ToDoItem toDoItem)

    def delete(ToDoItem toDoItem)
}
