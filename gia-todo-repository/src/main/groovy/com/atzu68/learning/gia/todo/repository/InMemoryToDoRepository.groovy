package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

class InMemoryToDoRepository implements ToDoRepository {
    private AtomicLong currentId = new AtomicLong()
    private ConcurrentMap<Long, ToDoItem> toDos =
            new ConcurrentHashMap<>()

    @Override
    List<ToDoItem> findAll() {
        return toDos.values().sort()
    }

    @Override
    ToDoItem findById(Long id) {
        return toDos[id]
    }

    @Override
    Long insert(ToDoItem toDoItem) {
        def id = currentId.incrementAndGet()
        toDoItem.id = id
        toDos[id] = toDoItem

        return id
    }

    @Override
    def update(ToDoItem toDoItem) {
        toDos.replace(toDoItem.id, toDoItem)
    }

    @Override
    def delete(ToDoItem toDoItem) {
        toDos.remove(toDoItem.id)
    }
}
