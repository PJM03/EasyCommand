package com.github.pjm03.easycommand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 기본적인 텍스트 명령어의 구조가 갖춰진 추상 클래스
 *
 * @author 박정민(<a href = "https://github.com/pjm03">GITHUB</a>)
 * @version 1.0.0
 * */
@Getter
@RequiredArgsConstructor
@ToString
public abstract class AbstractCommand implements ICommand {
    /**
     * 이름
     * */
    private final String command;
    /**
     * 별명(alias)
     * */
    private final Set<String> aliases;
    /**
     * 설명
     * */
    private final String description;
    /**
     * 하위 명령어
     * */
    private final Map<String, AbstractCommand> subCommands;
    /**
     * 하위 명령어 aliases map
     * */
    private final Map<String, AbstractCommand> subCommandAliases;
    /**
     * 인자값에 해당하는 하위 명령어를 가져오는 메서드
     *
     * @param subCommand 하위 명령어 이름/별명
     * @return subCommand에 해당하는 하위 명령어. 없다면 null
     * */
    public AbstractCommand getSubCommand(String subCommand) {
        return subCommands.getOrDefault(subCommand, subCommandAliases.get(subCommand));
    }
}
