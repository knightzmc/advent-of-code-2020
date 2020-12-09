package me.bristermitten.aoc.day8;

public class JMPInstruction implements Instruction{

    private final int adjustment;
    
    public int getAdjustment() {
        return adjustment;
    }
    
    @Override
    public void execute(Machine machine) {
        machine.setNextInstruction(machine.getCurrentInstruction() + adjustment);

    }

    public JMPInstruction(int adjustment) {
        this.adjustment = adjustment;
    }
    
    @Override
    public String toString() {
        return "JMPInstruction(" + (adjustment < 0 ? adjustment : "+" + adjustment) + ")";
    }
}
