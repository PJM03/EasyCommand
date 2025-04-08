package com.github.pjm03.easycommand.typefixed;

import com.github.pjm03.easycommand.AbstractCommand;

import java.util.Map;
import java.util.Set;

public class TypeFixedCommand extends AbstractCommand {
    public TypeFixedCommand(String command, Set<String> aliases, String description, Map<String, AbstractCommand> subCommands, Map<String, AbstractCommand> subCommandAliases) {
        super(command, aliases, description, subCommands, subCommandAliases);
    }

    @Override
    public void execute(String[] args) {

    }
}
