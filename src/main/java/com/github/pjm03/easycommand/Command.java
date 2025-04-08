package com.github.pjm03.easycommand;

import java.util.Map;

/**
 * 모든 명령어의 필수 메서드 인터페이스.
 * @author 박정민(<a href = "https://github.com/pjm03">GITHUB</a>)
 * @version 1.0.0
 * */
public interface Command {
    /**
     * 더 이상 하위 명령어가 없을 때, 최종적으로 명령어를 실
     *
     * @param args 명령어 인자
     * */
    void execute(String[] args);
    /**
     * 하위 명령어 Map을 반환하는 메서드
     * @return 명령어 혹은 alias가 key로 구성되어 있는 Map
     * */
    Map<String, ? extends Command> getSubCommands();
}