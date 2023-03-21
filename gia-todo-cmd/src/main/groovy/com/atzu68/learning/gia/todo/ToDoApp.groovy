package com.atzu68.learning.gia.todo

import com.atzu68.learning.gia.todo.utils.CommandLineInput
import com.atzu68.learning.gia.todo.utils.CommandLineInputHandler
import org.apache.commons.lang3.CharUtils

import static com.atzu68.learning.gia.todo.utils.CommandLineInput.EXIT
import static com.atzu68.learning.gia.todo.utils.CommandLineInput.getCommandLineInputForInput

class ToDoApp {

    static final char DEFAULT_INPUT = '\u0000' as char

    static void main(String[] args) {
        def commandLineInputHandler = new CommandLineInputHandler()
        CommandLineInput command = null

        while (command != EXIT) {
            commandLineInputHandler.printOptions()
            def input = commandLineInputHandler.readInput()
            input = CharUtils.toChar(input, DEFAULT_INPUT).toString()
            command = getCommandLineInputForInput(input)
            commandLineInputHandler.processInput(command)
        }
    }
}
