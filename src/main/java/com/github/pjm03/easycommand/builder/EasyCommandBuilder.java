package com.github.pjm03.easycommand.builder;

import com.github.pjm03.easycommand.AbstractCommand;
import com.github.pjm03.easycommand.EasyCommand;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

@Getter
public class EasyCommandBuilder {
    public static EasyCommandBuilder create(String command) {
        return new EasyCommandBuilder(command);
    }

    private final String command;
    private final Set<String> aliases = new HashSet<>();
    private String description;
    private final Map<String, AbstractCommand> subCommands = new HashMap<>();
    private Consumer<String[]> consumer;

    EasyCommandBuilder(String command) {
        this.command = command;
    }

    public EasyCommandBuilder aliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    public EasyCommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public EasyCommandBuilder addSubCommand(AbstractCommand subCommand) {
        if (this.subCommands.containsKey(subCommand.getCommand())) throw new IllegalArgumentException("Sub command \"%s\" is already registered".formatted(subCommand.getCommand()));
        for (String alias : subCommand.getAliases()) {
            if (this.subCommands.containsKey(alias)) throw new IllegalArgumentException("Sub command alias \"%s\" is already registered".formatted(alias));
        }

        this.subCommands.put(subCommand.getCommand(), subCommand);
        for (String alias : subCommand.getAliases()) {
            this.subCommands.put(alias, subCommand);
        }

        return this;
    }

    public EasyCommandBuilder execute(Consumer<String[]> consumer) {
        this.consumer = consumer;
        return this;
    }

    public AbstractCommand build(boolean register) {
        AbstractCommand abstractCommand = new AbstractCommand(this.command, this.aliases, this.description, subCommands) {
            @Override
            public void execute(String[] args) {
                EasyCommandBuilder.this.consumer.accept(args);
            }
        };

        if (register) EasyCommand.register(abstractCommand);
        return abstractCommand;
    }
}
