package com.github.pjm03.easycommand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@ToString
public abstract class AbstractCommand implements ICommand {
    private final String command;
    private final Set<String> aliases;
    private final String description;
    private final Map<String, AbstractCommand> subCommands;
}
