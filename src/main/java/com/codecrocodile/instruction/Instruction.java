/*
 * @(#)AbstractInstruction.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

import java.util.Stack;

import com.codecrocodile.main.PpsTask;

/**
 * @author Chris Hatton
 */
public abstract class Instruction {

	private Integer code;
	
	private String name;
	
	private Integer argument;
	
	private boolean requiresArgument;
	
	
	/**
     * Constructor
     */
    public Instruction(Integer code, String name) {
		this.code = code;
		this.name = name;
    }

    public abstract void execute(PpsTask ppsTask, Stack<Integer> taskStack);

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getArgument() {
		return argument;
	}

	public void setArgument(Integer argument) {
		this.argument = argument;
	}

	public boolean isRequiresArgument() {
		return requiresArgument;
	}
	
	public void setRequiresArgument(boolean requiresArgument) {
		this.requiresArgument = requiresArgument;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return name;
	}
    
}
