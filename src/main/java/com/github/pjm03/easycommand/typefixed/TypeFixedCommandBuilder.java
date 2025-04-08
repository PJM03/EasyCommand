package com.github.pjm03.easycommand.typefixed;

import com.github.pjm03.easycommand.EasyCommandBuilder;

public class TypeFixedCommandBuilder {
    private final EasyCommandBuilder easyCommandBuilder;

    TypeFixedCommandBuilder(String command) {
        this.easyCommandBuilder = EasyCommandBuilder.create(command);
    }

    public TypeFixedCommandBuilder aliases(String... aliases) {
        easyCommandBuilder.aliases(aliases);
        return this;
    }

    public TypeFixedCommandBuilder description(String description) {
        easyCommandBuilder.description(description);
        return this;
    }

    public TypeFixedCommandBuilder addSubCommand() {

    }
}
