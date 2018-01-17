/*
 * @(#)TestInstructionFactory.java			12 Jan 2014
 *
 */
package com.codecrocodle.instruction;


import org.junit.Assert;
import org.junit.Test;

import com.codecrocodile.instruction.Add;
import com.codecrocodile.instruction.AssignId;
import com.codecrocodile.instruction.Divide;
import com.codecrocodile.instruction.Dup;
import com.codecrocodile.instruction.Instruction;
import com.codecrocodile.instruction.InstructionFactory;
import com.codecrocodile.instruction.InstructionFactoryIF;
import com.codecrocodile.instruction.Multiply;
import com.codecrocodile.instruction.Pop;
import com.codecrocodile.instruction.Push;
import com.codecrocodile.instruction.PushTask;
import com.codecrocodile.instruction.Subtract;

/**
 * @author Chris Hatton
 */
public class TestInstructionFactory {

	@Test
	public void testCreate() {
		InstructionFactoryIF instructionFactoryIF = new InstructionFactory();
		
		Instruction instruction1 = instructionFactoryIF.createInstruction(1);
		Instruction instruction2 = instructionFactoryIF.createInstruction(2);
		Instruction instruction3 = instructionFactoryIF.createInstruction(3);
		Instruction instruction4 = instructionFactoryIF.createInstruction(4);
		Instruction instruction5 = instructionFactoryIF.createInstruction(5);
		Instruction instruction6 = instructionFactoryIF.createInstruction(6);
		Instruction instruction7 = instructionFactoryIF.createInstruction(7);
		Instruction instruction8 = instructionFactoryIF.createInstruction(8);
		Instruction instruction9 = instructionFactoryIF.createInstruction(9);
		
		Assert.assertEquals(instruction1.getClass(), Push.class);
		Assert.assertEquals(instruction2.getClass(), Pop.class);
		Assert.assertEquals(instruction3.getClass(), Dup.class);
		Assert.assertEquals(instruction4.getClass(), Add.class);
		Assert.assertEquals(instruction5.getClass(), Subtract.class);
		Assert.assertEquals(instruction6.getClass(), Multiply.class);
		Assert.assertEquals(instruction7.getClass(), Divide.class);
		Assert.assertEquals(instruction8.getClass(), AssignId.class);
		Assert.assertEquals(instruction9.getClass(), PushTask.class);
	}
}
