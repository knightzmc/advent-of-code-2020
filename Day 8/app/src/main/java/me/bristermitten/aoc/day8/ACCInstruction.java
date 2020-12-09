package me.bristermitten.aoc.day8;

public class ACCInstruction implements Instruction {

    private final int adjustment;

    @Override
    public void execute(Machine machine) {
        machine.setAccumulatorValue(machine.getAccumulatorValue() + adjustment);

    
        machine.setNextInstruction(machine.getCurrentInstruction() + 1);
    }

    public ACCInstruction(int adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public String toString() {
        return "ACCInstruction(" + adjustment + ")";
    }
}
