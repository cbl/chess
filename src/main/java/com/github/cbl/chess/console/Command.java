package com.github.cbl.chess.console;

import java.util.Map;
import java.util.HashMap;

public abstract class Command
{
    public static class Arguments {
        private Map<String, String> arguments;

        public Arguments(String[] raw) {

        }
    }

    public abstract int handle(Arguments arguments);
    protected abstract String[] arguments();
    protected abstract String name();

    public String getName() {
        return this.name();
    }

    public Map<String[], String> getArguments() {
        Map<String[], String> arguments = new HashMap<String[], String>();

        for(String str : this.arguments()) {
            String[] parts = str.split(":");
            arguments.put(parts[0].split(","), parts[1]);
        }

        return arguments;
    }
}