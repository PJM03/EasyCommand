package com.github.pjm03.easycommand;

import lombok.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Command들의 등록 및 파싱을 담당하는 클래스
 *
 * @author 박정민(<a href="https://github.com/pjm03">GITHUB</a>)
 * @version 1.0.0
 * */
public class EasyCommand {
    /**
     * 모든 명령어 객체들이 등록되어 있는 Map
     * */
    private static final Map<String, AbstractCommand> COMMAND_MAP = new HashMap<>();
    private static final Map<String, AbstractCommand> COMMAND_ALIAS_MAP = new HashMap<>();

    /**
     * 문자열 명령어 분석 및 실행
     *
     * @param line 명령어 문자열 (ex. /test hello 123)
     * @throws IllegalArgumentException 명령어를 찾을 수 없음.
     * */
    public static void executeCommand(@NonNull String line) {
        if (line.startsWith("/")) line = line.substring(1);

        String[] split = line.split(" ");
        executeCommand(split[0], Arrays.copyOfRange(split, 1, split.length));
    }

    /**
     * 분석된 명령어 실행
     *
     * @param command 명령어 이름 (ex. test)
     * @param args 명령어 인자 (ex. [hello, 123])
     * @throws IllegalArgumentException command 값에 해당하는 명령어를 찾을 수 없음.
     * */
    public static void executeCommand(@NonNull String command, @NonNull String[] args) {
        AbstractCommand abstractCommand = COMMAND_MAP.getOrDefault(command, COMMAND_ALIAS_MAP.get(command));
        if (abstractCommand == null) throw new IllegalArgumentException("Command \"%s\" is not found.".formatted(command));

        while (args.length > 0) {
            String arg = args[0];
            AbstractCommand subCommand = abstractCommand.getSubCommand(arg);
            if (subCommand == null) break;

            args = Arrays.copyOfRange(args, 1, args.length);
            abstractCommand = subCommand;
        }

        abstractCommand.execute(args);
    }

    /**
     * 명령어 등록
     *
     * @param abstractCommand 등록되지 않은 명령어 객체
     * @throws IllegalArgumentException 명령어의 주 이름 혹은 별명(alias)가 이미 등록되어 있을 때
     * */
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

    /**
     * 명령어 등록 해제
     *
     * @param abstractCommand 등록된 명령어 객체
     * */
    public static void unregister(AbstractCommand abstractCommand) {
        COMMAND_MAP.remove(abstractCommand.getCommand());
        for (String alias : abstractCommand.getAliases()) {
            COMMAND_MAP.remove(alias);
        }
    }
}
