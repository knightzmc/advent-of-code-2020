package me.bristermitten.aoc.day8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public List<Instruction> parse(String text) {
        return Arrays.stream(text.split("\n")).map(this::parseLine).collect(Collectors.toList());
    }

    private Instruction parseLine(String line) {
        String jump = line.substring(4); // [int] [num]
        int adj = Integer.parseInt(jump);
        if (line.startsWith("nop")) {
            return new NOPInstruction(adj);
        }
        if (line.startsWith("acc")) {
            return new ACCInstruction(adj);
        }
        if (line.startsWith("jmp")) {
            return new JMPInstruction(adj);
        }

        throw new UnsupportedOperationException("Unknown instruction " + line);
    }
}
