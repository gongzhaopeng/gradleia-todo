package com.atzu68.learning.gia.todo.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
@Sortable(includes = "id")
class ToDoItem {
    Long id
    String name
    boolean completed
}
