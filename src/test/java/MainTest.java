import com.github.pjm03.easycommand.AbstractCommand;
import com.github.pjm03.easycommand.EasyCommand;
import com.github.pjm03.easycommand.builder.EasyCommandBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    AbstractCommand lastRegistered;

    @AfterEach
    void tearDown() {
        if (lastRegistered != null) {
            EasyCommand.unregister(lastRegistered);
            lastRegistered = null;
        }
    }

    @Test
    void testCommandExecution() {
        AtomicBoolean executed = new AtomicBoolean(false);

        lastRegistered = EasyCommandBuilder.create("hello")
                .description("Say Hello")
                .execute(args -> executed.set(true))
                .build(true);

        EasyCommand.executeCommand("hello");

        assertTrue(executed.get(), "Command should have been executed");
    }

    @Test
    void testCommandWithArguments() {
        AtomicBoolean correctArgs = new AtomicBoolean(false);

        lastRegistered = EasyCommandBuilder.create("greet")
                .execute(args -> {
                    if (args.length == 1 && args[0].equals("world")) {
                        correctArgs.set(true);
                    }
                })
                .build(true);

        EasyCommand.executeCommand("greet world");

        assertTrue(correctArgs.get(), "Arguments should be passed correctly");
    }

    @Test
    void testSubCommandExecution() {
        AtomicBoolean subExecuted = new AtomicBoolean(false);

        AbstractCommand subCommand = EasyCommandBuilder.create("start")
                .execute(args -> subExecuted.set(true))
                .build(false);

        lastRegistered = EasyCommandBuilder.create("game")
                .addSubCommand(subCommand)
                .execute(args -> fail("Should not call root when sub exists"))
                .build(true);

        EasyCommand.executeCommand("game start");

        assertTrue(subExecuted.get(), "Subcommand should be executed");
    }

    @Test
    void testDuplicateCommandRegistrationThrows() {
        lastRegistered = EasyCommandBuilder.create("dup")
                .execute(args -> {})
                .build(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                EasyCommandBuilder.create("dup")
                        .execute(args -> {})
                        .build(true)
        );

        assertTrue(ex.getMessage().contains("already registered"));
    }

    @Test
    void testUnknownCommandThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                EasyCommand.executeCommand("unknown")
        );

        assertTrue(ex.getMessage().contains("not found"));
    }
}
