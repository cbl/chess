package com.github.cbl.chess.console;

import com.github.cbl.chess.util.Observer;
import com.github.cbl.chess.console.commands.*;

import java.util.Scanner;
import java.util.Arrays;

public class CLI
{
    private Command[] commands = {
        new PlayCommand()
    };

    public CLI() {

    }

    public void run() {
        Scanner scanner = new Scanner(System.in); 

        while(true) {
            System.out.print("Enter Command:\n> ");
            String command = scanner.nextLine();
            this.handle(command);
        }
    }

    public int handle(String action) {
        String[] parts = action.split(" ");
        String name = parts[0];
        Command command = this.findByName(name);

        if(command == null) {
            System.out.println("Command ["+name+"] not found.");
            return 1;
        }

        Command.Arguments arguments = new Command.Arguments(
            Arrays.copyOfRange(parts, 1, parts.length)
        );
        return command.handle(arguments);
    }

    public Command findByName(String name) {
        
        for(Command command: this.commands) {
            if(name.equals(command.getName())){
                return command;
            }
        }

        return null;
    }
}