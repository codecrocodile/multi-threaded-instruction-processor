/*
 * @(#)Dup.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

import java.util.Stack;

import com.codecrocodile.main.PpsTask;

/**
 * @author Chris Hatton
 */
public class Dup extends Instruction {

	/**
	 * Constructor
	 */
    public Dup(Integer code, String name) {
	    super(code, name);
    }

	/* 
	 * @see com.codecrocodile.instruction.Instruction#execute(java.util.Stack)
	 */
	@Override
	public void execute(PpsTask ppsTask, Stack<Integer> taskStack) {
		Integer topValue = taskStack.peek();
		taskStack.push(Integer.valueOf(topValue));
	}

}
