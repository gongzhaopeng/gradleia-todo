package com.atzu68.learning.gia.todo.web


import geb.spock.GebSpec
import org.openqa.selenium.Keys

class ToDoTest extends GebSpec {
    def 'can add a todo item via homepage'() {
        given:
        to ToDoHomepage

        when:
        $('form').name = 'Write functional tests'
        $('form').name << Keys.ENTER

        then:
        waitFor { at ToDoInsert }
    }
}