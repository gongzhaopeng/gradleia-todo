package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem
import org.testng.annotations.*

import static org.testng.Assert.*

class InMemoryToDoRepositoryNGTest {
    private ToDoRepository inMemoryToDoRepository

    @BeforeTest
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
