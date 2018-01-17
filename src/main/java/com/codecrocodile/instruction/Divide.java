/*
 * @(#)Divide.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

import java.util.Stack;

import com.codecrocodile.main.PpsTask;

/**
 * @author Chris Hatton
 */
public class Divide extends Instruction {

	/**
	 * Constructor
	 */
    public Divide(Integer code, String name) {
	    super(code, name);
    }

	/* 
	 * @see com.codecrocodile.instruction.Instruction#execute(java.util.Stack)
	 */
	@Override
	public void execute(PpsTask ppsTask, Stack<Integer> taskStack) {
		Integer firstValue = taskStack.pop();
		Integer secondValue = taskStack.pop();
		// TODO If this was production code then we would have to deal with division by zero exceptions. 
		//      In the interest of keeping this simple, we won't handle these cases. 
		taskStack.push(firstValue / secondValue);
	}

}
