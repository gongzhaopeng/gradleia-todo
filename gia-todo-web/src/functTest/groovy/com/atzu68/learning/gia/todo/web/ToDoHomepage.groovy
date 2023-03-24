package com.atzu68.learning.gia.todo.web

import geb.*

class ToDoHomepage extends Page {
    static url = "http://localhost:8080/todo"
    static at = { title == "To Do application" }
}