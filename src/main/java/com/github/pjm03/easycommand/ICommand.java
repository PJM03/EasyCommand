package com.github.pjm03.easycommand;

import java.util.List;
import java.util.Map;

public interface ICommand {
    void execute(String[] args);
    Map<String, ? extends ICommand> getSubCommands();
}