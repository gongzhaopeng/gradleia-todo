package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class InMemoryToDoRepositoryTest {
    private ToDoRepository inMemoryToDoRepositoryTest

    @BeforeEach
    void setUp() {
        inMemoryToDoRepositoryTest = new InMemoryToDoRepository()
    }

    @Test
    void insertToDoItem() {
        ToDoItem newToDoItem = [name: 'Write unit tests']
        def newId = inMemoryToDoRepositoryTest.insert(newToDoItem)
        assertNotNull(newId)

        def persistedToDoItem = inMemoryToDoRepositoryTest.findById(newId)
        assertNotNull(persistedToDoItem)
        assertEquals(newToDoItem, persistedToDoItem)
    }
}
