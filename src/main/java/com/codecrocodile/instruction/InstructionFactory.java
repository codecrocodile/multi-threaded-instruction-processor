/*
 * @(#)InstructionFactory.java			10 Jan 2014
 *
 */
package com.codecrocodile.instruction;

/**
 * @author Chris Hatton
 */
public class InstructionFactory implements InstructionFactoryIF {

	/*
	 * @see com.codecrocodile.instruction.InstructionFactoryIF#createInstruction(int)
	 */
    public Instruction createInstruction(int code) {
	    Instruction instruction = null;

	    switch (code) {
		case 1:
			instruction = new Push(1, "PUSH");
			instruction.setRequiresArgument(true);
			break;
		case 2:
			instruction = new Pop(2, "POP");
			break;
		case 3:
			instruction = new Dup(3, "DUP");
			break;
		case 4:
			instruction = new Add(4, "ADD");
			break;
		case 5:
			instruction = new Subtract(5, "SUBTRACT");
			break;
		case 6:
			instruction = new Multiply(6, "MULTIPLY");
			break;
		case 7:
			instruction = new Divide(7, "DIVIDE");
			break;
		case 8:
			instruction = new AssignId(8, "ASSIGN_ID");
			break;
		case 9:
			instruction = new PushTask(9, "PUSH_TASK");
			break;			
		default:
			throw new IllegalArgumentException("'" + Integer.toString(code) + "' is not a legal instruction code.");
		}
	    
	    return instruction;
    }

}
