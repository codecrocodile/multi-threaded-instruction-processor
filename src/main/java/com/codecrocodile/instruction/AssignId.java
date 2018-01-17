/*
 * @(#)AssignId.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

import java.util.Stack;

import com.codecrocodile.main.PpsTask;

/**
 * @author Chris Hatton
 */
public class AssignId extends Instruction {

	/**
	 * Constructor
	 */
    public AssignId(Integer code, String name) {
	    super(code, name);
    }

	/* 
	 * @see com.codecrocodile.instruction.Instruction#execute(java.util.Stack)
	 */
	@Override
	public void execute(PpsTask ppsTask, Stack<Integer> taskStack) {
		Integer taskResultId = taskStack.pop();
		ppsTask.setTaskResultId(taskResultId);
	}

}
