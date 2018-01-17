/*
 * @(#)RunnAll.java			12 Jan 2014
 */
package com.codecrocodle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.codecrocodle.instruction.TestInstructionFactory;
import com.codecrocodle.main.TestPpsProcessor;

/**
 * @author Chris Hatton
 */
@RunWith(Suite.class)
@SuiteClasses({
    TestInstructionFactory.class,
    TestPpsProcessor.class,
})
public class RunnAll {

}
