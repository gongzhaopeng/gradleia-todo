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
        def items = System.getProperty('items')?.toInteger() ?: 1
        createAndInsertToDoItems(items)
        def toDoItems = inMemoryToDoRepository.findAll()
        assertEquals(items, toDoItems.size())
    }

    private void createAndInsertToDoItems(int items) {
        println "Creating $items To Do items."

        (1..items).each {
            ToDoItem toDoItem = [name: "To Do task $it"]
            inMemoryToDoRepository.insert(toDoItem)
        }
    }
}
