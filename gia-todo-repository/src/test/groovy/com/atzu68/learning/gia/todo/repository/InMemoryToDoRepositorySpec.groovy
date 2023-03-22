package com.atzu68.learning.gia.todo.repository

import com.atzu68.learning.gia.todo.model.ToDoItem
import spock.lang.*

class InMemoryToDoRepositorySpec extends Specification {
    def 'Insert To Do item'() {
        given:
        def inMemoryToDoRepository = new InMemoryToDoRepository()

        when:
        ToDoItem newToDoItem = [name: 'Write unit tests']
        def newId = inMemoryToDoRepository.insert(newToDoItem)

        then:
        def persistedToDoItem = inMemoryToDoRepository.findById(newId)
        newId != null
        persistedToDoItem != null
        newToDoItem == persistedToDoItem
    }
}
