package com.ssomar.score.commands.runnable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.CommandNameMatchTest.named;
import static org.junit.jupiter.api.Assertions.*;

/**
 * getCommand looks the names up by their first word instead of asking every command: same command as the linear scan it
 * replaced (longest name, first registered on a tie), and the index follows changes of the commands list.
 */
class CommandManagerIndexTest {

    /** The getCommand of 2026-09-14 */
    private static SCommand linearScan(List<SCommand> commands, String entry) {
        SCommand best = null;
        int bestLen = -1;
        for (SCommand command : commands) {
            int len = command.matchedNameLength(entry);
            if (len > bestLen) {
                bestLen = len;
                best = command;
            }
        }
        return best;
    }

    private static CommandManager<SCommand> manager(List<SCommand> commands) {
        CommandManager<SCommand> m = new CommandManager<SCommand>() {};
        m.setCommands(commands);
        return m;
    }

    @Test
    void sameCommandAsTheLinearScan() {
        List<SCommand> commands = new ArrayList<>(Arrays.asList(
                named("BREAK"), named("BREAK"), named("TELEPORT"), named("TELEPORT POSITION", "TELEPORT_POSITION"),
                named("SETBLOCK"), named("SETBLOCKPOS"), named("IF"), named("SET-ITEM"), named("MOB_AROUND"), named("AROUND"),
                named("SENDMESSAGE"), named("DELAY"), named("DELAYTICK"), named("2X"), named("[OFFLINE]"), named("score run-block-command")));
        CommandManager<SCommand> m = manager(commands);
        String[] entries = {"BREAK", "break", "BREAK:1", "BREAKING", "breakinradius 3", "TELEPORT POSITION 1 2 3", "teleport 1 2",
                "TELEPORT_POSITION 1", "TELEPORT POSITIONS", "SETBLOCK stone", "SETBLOCKPOS 1 2 3", "SETBLOCK_X", "IF %a%=1 SENDMESSAGE hi", "IF",
                "SET-ITEM x", "SET x", "SET-ITEMS", "MOB_AROUND 3", "AROUND 3", "SENDMESSAGE&ahello", "DELAY 5", "DELAYTICK 5", "2X go",
                "[OFFLINE] SENDMESSAGE", "score run-block-command block:world,1,2,3 BREAK", "SCORE RUN-BLOCK-COMMAND x", "score run",
                "ıF %a%=1", "ſETBLOCK stone", "ßREAK", "éBREAK", "", " BREAK", "%player% BREAK", "minecraft:tp @s", "tp @s ~ ~ ~"};
        for (String entry : entries) {
            assertSame(linearScan(commands, entry), m.getCommand(entry).orElse(null), entry);
        }
        assertSame(commands.get(0), m.getCommand("BREAK").get(), "a tie keeps the first registered command");
        assertEquals(Optional.empty(), m.getCommand(null));
    }

    @Test
    void indexFollowsTheCommandsList() {
        List<SCommand> commands = new ArrayList<>(Arrays.asList(named("BREAK")));
        CommandManager<SCommand> m = manager(commands);
        assertFalse(m.getCommand("LAUNCH ARROW").isPresent());
        SCommand launch = named("LAUNCH");
        commands.add(launch);
        assertSame(launch, m.getCommand("LAUNCH ARROW").orElse(null), "command added to the list");
        SCommand other = named("LAUNCH");
        m.setCommands(new ArrayList<>(Arrays.asList(other)));
        assertSame(other, m.getCommand("LAUNCH ARROW").orElse(null), "list replaced");
    }
}
