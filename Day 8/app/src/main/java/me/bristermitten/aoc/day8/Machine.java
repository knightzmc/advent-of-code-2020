package me.bristermitten.aoc.day8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Machine {
    private int accumulatorValue;
    private int currentlyExecuting = 0;
    private List<Instruction> instructions = new ArrayList<>();
    private Set<Integer> executed = new HashSet<>();


    public void resetState() {
        executed.clear();
        currentlyExecuting = 0;
        accumulatorValue = 0;
    }
    
    public boolean execute() {

        if (executed.contains(currentlyExecuting)) {
            return false;
        }

        executed.add(currentlyExecuting);
        instructions.get(currentlyExecuting).execute(this); //This mutates the currentlyExecuting

        if (currentlyExecuting >= instructions.size() - 1) {
            return true;
        }

        return execute();
    }

    public int getAccumulatorValue() {
        return accumulatorValue;
    }

    public void setAccumulatorValue(int accumulatorValue) {
        this.accumulatorValue = accumulatorValue;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public int getCurrentInstruction() {
        return currentlyExecuting;
    }

    public void setNextInstruction(int index) {
        this.currentlyExecuting = index;
    }
}
