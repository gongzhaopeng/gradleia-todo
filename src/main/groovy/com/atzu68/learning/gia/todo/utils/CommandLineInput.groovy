package com.atzu68.learning.gia.todo.utils

enum CommandLineInput {
    FIND_ALL('a'),
    FIND_BY_ID('f'),
    INSERT('i'),
    UPDATE('u'),
    DELETE('d'),
    EXIT('e')

    private final static Map<String, CommandLineInput> INPUTS

    static {
        INPUTS = values().collectEntries { [it.shortCmd, it] }
    }

    final String shortCmd

    private CommandLineInput(String shortCmd) {
        this.shortCmd = shortCmd
    }

    static CommandLineInput getCommandLineInputForInput(String input) {
        return INPUTS[input]
    }
}
