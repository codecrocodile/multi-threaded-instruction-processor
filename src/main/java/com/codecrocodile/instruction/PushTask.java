/*
 * @(#)PushTask.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

import java.util.Stack;

import com.codecrocodile.main.PpsTask;

/**
 * @author Chris Hatton
 */
public class PushTask extends Instruction {
	
	/**
	 * Constructor
	 */
    public PushTask(Integer code, String name) {
	    super(code, name);
    }

	/* 
	 * @see com.codecrocodile.instruction.Instruction#execute(com.codecrocodile.main.PpsTask, java.util.Stack)
	 */
    @Override
    public void execute(PpsTask ppsTask, Stack<Integer> taskStack) {
    	Integer taskResultId = taskStack.pop();
    	Integer taskResult = ppsTask.getTaskResult(taskResultId);
    	taskStack.push(taskResult);
    }

}
