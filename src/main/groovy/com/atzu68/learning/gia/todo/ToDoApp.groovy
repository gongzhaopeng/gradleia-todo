package com.atzu68.learning.gia.todo

import com.atzu68.learning.gia.todo.utils.CommandLineInput
import com.atzu68.learning.gia.todo.utils.CommandLineInputHandler

import static com.atzu68.learning.gia.todo.utils.CommandLineInput.EXIT
import static com.atzu68.learning.gia.todo.utils.CommandLineInput.getCommandLineInputForInput

class ToDoApp {

    static void main(String[] args) {
        def commandLineInputHandler = new CommandLineInputHandler()
        CommandLineInput command = null

        while (command != EXIT) {
            commandLineInputHandler.printOptions()
            def input = commandLineInputHandler.readInput()
            command = getCommandLineInputForInput(input)
            commandLineInputHandler.processInput(command)
        }
    }
}
