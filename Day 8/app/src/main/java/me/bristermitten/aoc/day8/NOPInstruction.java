package me.bristermitten.aoc.day8;

public class NOPInstruction implements Instruction {
    private final int adjustment;

    public int getAdjustment() {
        return adjustment;
    }

    public NOPInstruction(int adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public void execute(Machine machine) {
        machine.setNextInstruction(machine.getCurrentInstruction() + 1);
    }

    @Override
    public String toString() {
        return "NOPInstruction";
    }
}