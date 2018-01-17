/*
 * @(#)PpsTask.java			10 Jan 2014
 *
 */
package com.codecrocodile.main;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codecrocodile.instruction.Instruction;
import com.codecrocodile.instruction.InstructionFactoryIF;

/**
 * @author Chris Hatton
 */
public class PpsTask implements Callable<PpsTask> {
	
	private Log log = LogFactory.getLog(PpsProcessor.class);

	private PpsProcessor ppsProcessor;
	
	private List<Integer> codesAndArgumentsBatch;
	
	private Stack<Integer> taskStack = new Stack<>();
	
	private Integer taskResultId;
	
	
	/**
     * Constructor
     */
    public PpsTask(PpsProcessor ppsProcessor, List<Integer> codesAndArgumentsBatch) {
		this.ppsProcessor = ppsProcessor;
		this.codesAndArgumentsBatch = codesAndArgumentsBatch;
    }
    
	/* 
	 * @see java.util.concurrent.Callable#call()
	 */
    @Override
    public PpsTask call() throws Exception {
    	taskStack.clear();
    	List<Instruction> instructions = createInstructions(codesAndArgumentsBatch);
    	
    	for (Instruction instruction : instructions) {
        	if (ppsProcessor.isDebugOn()) {
        		printStack(instruction, true);
        	}
        	
    		instruction.execute(this, taskStack);
    		
        	if (ppsProcessor.isDebugOn()) {
        		printStack(instruction, false);
        	}
    	}
    	
    	if (taskStack.size() > 0) {
    		ppsProcessor.setTaskResult(taskResultId, taskStack.peek());	
    	} else {
    		ppsProcessor.setTaskResult(taskResultId, null);
    	}
    	
    	
	    return this;
    }
    
    private List<Instruction> createInstructions(List<Integer> codesAndArgumentsBatch) {
    	log.info("Entering: List<Instruction> createInstructions(List<Integer> codesAndArgumentsBatch)");
    	
    	List<Instruction> instructionList = new ArrayList<>();
    	
    	ListIterator<Integer> listIterator = codesAndArgumentsBatch.listIterator();
    	while (listIterator.hasNext()) {
	        Integer code = listIterator.next();
	        InstructionFactoryIF instructionFactory = ppsProcessor.getInstructionFactory();
	        Instruction instruction = instructionFactory.createInstruction(code);
	        if (instruction.isRequiresArgument()) {
	        	Integer argument = listIterator.next();
	        	instruction.setArgument(argument);
	        }
	        
	        instructionList.add(instruction);
        }
    	
    	return instructionList;
    }
    
    private void printStack(Instruction instruction, boolean beforeInstruction) {
    	StringBuffer stackStr = new StringBuffer();
    	
    	String argumentStr = "";
    	if (instruction.getArgument() != null) {
    		argumentStr +=  " " + instruction.getArgument();
    	}
    	
    	stackStr.append("Operation " + instruction + argumentStr + " (Stack size = " + taskStack.size() + ") ");
    	
    	if (beforeInstruction) {
    		stackStr.append("before=");
    	} else {
    		stackStr.append("after=");
    	}
    	
    	for (int i = taskStack.size() - 1; i >= 0; i--) {
    		stackStr.append("[" + taskStack.get(i) + "]");
    	}
    	
    	log.info(stackStr.toString() + " : " + Thread.currentThread().getName());
    }
	
    public void setTaskResultId(Integer taskResultId) {
    	this.taskResultId = taskResultId;
	}
    
    public Integer getTaskResult(Integer taskResultId) {
    	return ppsProcessor.getTaskResult(taskResultId);
    }
    
	public List<Integer> getCodesAndArgumentsBatch() {
		return codesAndArgumentsBatch;
	}
	
    public Integer getTaskResult() {
    	if (taskStack.size() > 0) {
    		return taskStack.peek();	
    	} else {
    		return null;
    	}
    }

}
