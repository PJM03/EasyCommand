package com.github.pjm03.easycommand;

import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

/**
 * AbstractCommand의 Builder 클래스
 * @see com.github.pjm03.easycommand.AbstractCommand
 *
 * @author 박정민(<a href = "https://github.com/pjm03">GITHUB</a>)
 * @version 1.0.0
 * */
@Getter
public class EasyCommandBuilder {
    /**
     * builder를 생성
     *
     * @param command 명령어 이름
     * @return EasyCommandBuilder instance
     * */
    public static EasyCommandBuilder create(String command) {
        return new EasyCommandBuilder(command);
    }

    /**
     * 이름
     * */
    private final String command;
    /**
     * 별명
     * */
    private final Set<String> aliases = new HashSet<>();
    /**
     * 설명
     * */
    private String description;
    /**
     * 하위 명령어
     * */
    private final Map<String, AbstractCommand> subCommands = new HashMap<>();
    private final Map<String, AbstractCommand> subCommandAliases = new HashMap<>();
    /**
     * 실행 시 작동하는 consumer
     * */
    private Consumer<String[]> consumer;

    /**
     * 내부적으로 사용되는 생성자.
     *
     * @param command 명령어 이름
     * */
    EasyCommandBuilder(String command) {
        this.command = command;
    }

    /**
     * 명령어의 별명을 추가
     *
     * @param aliases 별명(가변 인자)
     * @return this(EasyCommandBuilder)
     * */
    public EasyCommandBuilder aliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    /**
     * 명령어의 설명을 추가
     *
     * @param description 설명
     * @return this(EasyCommandBuilder)
     * */
    public EasyCommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * 하위 명령어 추가
     *
     * @param subCommand 하위 명령어
     * @return this(EasyCommandBuilder)
     * */
    public EasyCommandBuilder addSubCommand(AbstractCommand subCommand) {
        if (this.subCommands.containsKey(subCommand.getCommand())) throw new IllegalArgumentException("Sub command \"%s\" is already registered".formatted(subCommand.getCommand()));
        for (String alias : subCommand.getAliases()) {
            if (this.subCommandAliases.containsKey(alias)) throw new IllegalArgumentException("Sub command alias \"%s\" is already registered".formatted(alias));
        }

        this.subCommands.put(subCommand.getCommand(), subCommand);
        for (String alias : subCommand.getAliases()) {
            this.subCommandAliases.put(alias, subCommand);
        }

        return this;
    }

    /**
     * 명령어의 실행 코드 추가
     *
     * @param consumer 분석 후 남은 인자를 소비하는 Function
     * @return this(EasyCommandBuilder)
     * */
    public EasyCommandBuilder execute(Consumer<String[]> consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * 하위 명령어를 포함해 완성된 명령어로 빌드
     *
     * @param register 자동으로 EasyCommand에 등록할지 여부
     * @return 완성된 AbstractCommand
     * @see com.github.pjm03.easycommand.EasyCommand
     * @see com.github.pjm03.easycommand.AbstractCommand
     * */
    public AbstractCommand build(boolean register) {
        AbstractCommand abstractCommand = new AbstractCommand(this.command, this.aliases, this.description, subCommands, subCommandAliases) {
            @Override
            public void execute(String[] args) {
                if(EasyCommandBuilder.this.consumer != null) EasyCommandBuilder.this.consumer.accept(args);
            }
        };

        if (register) EasyCommand.register(abstractCommand);
        return abstractCommand;
    }
}
