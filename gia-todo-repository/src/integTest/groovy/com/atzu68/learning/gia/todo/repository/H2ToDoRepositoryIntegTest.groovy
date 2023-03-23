package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class H2ToDoRepositoryIntegTest {
    private ToDoRepository h2ToDoRepository

    @BeforeEach
    void setUp() {
        h2ToDoRepository = new H2ToDoRepository()
    }

    @Test
    void insertToDoItem() {
        ToDoItem newToDoItem = [name: 'Write integration tests']
        def newId = h2ToDoRepository.insert(newToDoItem)
        newToDoItem.setId(newId)
        assertNotNull(newId)

        def persistedToDoItem = h2ToDoRepository.findById(newId)
        assertNotNull(persistedToDoItem)
        assertEquals(newToDoItem, persistedToDoItem)
    }
}
