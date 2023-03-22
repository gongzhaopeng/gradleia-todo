package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class InMemoryToDoRepositoryTest {
    private ToDoRepository inMemoryToDoRepository

    @BeforeEach
    void setUp() {
        inMemoryToDoRepository = new InMemoryToDoRepository()
    }

    @Test
    void insertToDoItem() {
        ToDoItem newToDoItem = [name: 'Write unit tests']
        def newId = inMemoryToDoRepository.insert(newToDoItem)
        assertNotNull(newId)

        def persistedToDoItem = inMemoryToDoRepository.findById(newId)
        assertNotNull(persistedToDoItem)
        assertEquals(newToDoItem, persistedToDoItem)
    }
}
