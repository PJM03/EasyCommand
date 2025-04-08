package com.github.pjm03.easycommand;

import lombok.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EasyCommand {
    private static final Map<String, AbstractCommand> COMMAND_MAP = new HashMap<>();

    public static void executeCommand(@NonNull String line) {
        if (line.startsWith("/")) line = line.substring(1);

        String[] split = line.split(" ");
        executeCommand(split[0], Arrays.copyOfRange(split, 1, split.length));
    }

    public static void executeCommand(@NonNull String command, @NonNull String[] args) {
        AbstractCommand abstractCommand = COMMAND_MAP.get(command);
        if (abstractCommand == null) throw new IllegalArgumentException("Command \"%s\" is not found.".formatted(command));


        while (args.length > 0 && !abstractCommand.getSubCommands().isEmpty()) {
            String arg = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            abstractCommand = abstractCommand.getSubCommands().get(arg);
        }

        abstractCommand.execute(args);
    }

    public static void register(AbstractCommand abstractCommand) {
        if (COMMAND_MAP.containsKey(abstractCommand.getCommand())) throw new IllegalArgumentException("Command \"%s\" is already registered".formatted(abstractCommand.getCommand()));
        for (String alias : abstractCommand.getAliases()) {
            if (COMMAND_MAP.containsKey(alias)) throw new IllegalArgumentException("Command alias \"%s\" is already registered".formatted(alias));
        }

        COMMAND_MAP.put(abstractCommand.getCommand(), abstractCommand);
        for (String alias : abstractCommand.getAliases()) {
            COMMAND_MAP.put(alias, abstractCommand);
        }
    }

    public static void unregister(AbstractCommand abstractCommand) {
        COMMAND_MAP.remove(abstractCommand.getCommand());
        for (String alias : abstractCommand.getAliases()) {
            COMMAND_MAP.remove(alias);
        }
    }
}
