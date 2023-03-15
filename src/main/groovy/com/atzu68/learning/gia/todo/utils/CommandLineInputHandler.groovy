package com.atzu68.learning.gia.todo.utils

import com.atzu68.learning.gia.todo.model.ToDoItem
import com.atzu68.learning.gia.todo.repository.InMemoryToDoRepository
import com.atzu68.learning.gia.todo.repository.ToDoRepository

import static com.atzu68.learning.gia.todo.utils.CommandLineInput.*

class CommandLineInputHandler {
    private ToDoRepository toDoRepository = new InMemoryToDoRepository()

    static void printOptions() {
        println "\n--- To Do Application ---"
        println "Please make a choice:"
        println "(a)ll items"
        println "(f)ind a specific item"
        println "(i)nsert a new item"
        println "(u)pdate an existing item"
        println "(d)elete an existing item"
        println "(e)xit"
    }

    static String readInput() {
        return System.console().readLine("> ")
    }

    void processInput(CommandLineInput input) {
        switch (input) {
            case FIND_ALL:
                printAllToDoItems(); break
            case FIND_BY_ID:
                printToDoItem(); break
            case INSERT:
                insertToDoItem(); break
            case UPDATE:
                updateToDoItem(); break
            case DELETE:
                deleteToDoItem(); break
            case EXIT: break
            default: handleUnknownInput()
        }
    }

    static private Long askForItemId() {
        println "Please enter the item ID:"

        return readInput().toLong()
    }

    static private ToDoItem askForNewToDoAction() {
        println "Please enter the name of the item:"

        ToDoItem toDoItem = [name: readInput()]
        return toDoItem
    }

    private void printAllToDoItems() {
        toDoRepository.findAll().with {
            if (empty)
                println "Nothing to do. Go relax!"
            else forEach { println it }
        }
    }

    private void printToDoItem() {
        findToDoItem()?.with { println it }
    }

    private ToDoItem findToDoItem() {
        def id = askForItemId();
        def toDoItem = toDoRepository.findById(id)

        if (!toDoItem) System.err.println "To do item with ID $id could not be found."

        return toDoItem
    }

    private void insertToDoItem() {
        askForNewToDoAction().with {
            toDoRepository.insert(it)
            println "Successfully inserted to do item with ID $id."
        }
    }

    private void updateToDoItem() {
        findToDoItem()?.with {
            println it
            println "Please enter the name of the item:"
            it.name = readInput()
            println "Please enter the done status the item:"
            it.completed = readInput().toBoolean()
            toDoRepository.update(it)
            println "Successfully updated to do item with ID $id."
        }
    }

    private void deleteToDoItem() {
        findToDoItem()?.with {
            toDoRepository.delete(it)
            System.out.println("Successfully deleted to do item with ID $id.");
        }
    }

    static private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}
