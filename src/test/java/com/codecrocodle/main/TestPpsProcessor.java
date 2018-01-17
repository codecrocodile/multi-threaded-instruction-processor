/*
 * @(#)PpsProcessorTest.java			11 Jan 2014
 *
 */
package com.codecrocodle.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.codecrocodile.main.PpsProcessor;
import com.codecrocodile.main.Result;

/**
 * @author Chris Hatton
 */
public class TestPpsProcessor {
	
	@Test(timeout=10000)
	public void testBasicExamples() {
		PpsProcessor processor = new PpsProcessor();
		processor.setDebugOn(true);
		
		List<Integer> sequence1 = new ArrayList<>();
		sequence1.add(1);
		sequence1.add(2);
		sequence1.add(1);
		sequence1.add(3);
		sequence1.add(4);
		
		
		List<Integer> sequence2 = new ArrayList<>();
		sequence2.add(1);
		sequence2.add(5);
		sequence2.add(1);
		sequence2.add(3);
		sequence2.add(5);
		
		List<Integer> sequence3 = new ArrayList<>();
		sequence3.add(1);
		sequence3.add(20);
		sequence3.add(1);
		sequence3.add(30);
		sequence3.add(6);
		
		List<Integer> sequence4 = new ArrayList<>();
		sequence4.add(1);
		sequence4.add(20);
		sequence4.add(1);
		sequence4.add(5);
		sequence4.add(7);
		
		processor.process(sequence1);
		processor.process(sequence2);
		processor.process(sequence3);
		processor.process(sequence4);
	}
	
	@Test(timeout=10000)
	public void testParallelExamples() {
		List<Integer> sequence1 = new ArrayList<>();
		sequence1.add(1);
		sequence1.add(100);
		sequence1.add(8);
		sequence1.add(1);
		sequence1.add(2);
		sequence1.add(1);
		sequence1.add(3);
		sequence1.add(4);
		
		List<Integer> sequence2 = new ArrayList<>();
		sequence2.add(1);
		sequence2.add(200);
		sequence2.add(8);
		sequence2.add(1);
		sequence2.add(4);
		sequence2.add(1);
		sequence2.add(5);
		sequence2.add(4);
		
		List<Integer> sequence3 = new ArrayList<>();
		sequence3.add(1);
		sequence3.add(300);
		sequence3.add(8);
		sequence3.add(1);
		sequence3.add(100);
		sequence3.add(9);
		sequence3.add(1);
		sequence3.add(200);
		sequence3.add(9);
		sequence3.add(4);
		
		for (int i = 0; i < 500; i++) {
			PpsProcessor processor = new PpsProcessor();
			processor.setDebugOn(true);
			processor.process(sequence3);
			processor.process(sequence2);
			processor.process(sequence1);
			
			Integer batch1ExpectedResult = 5;
			Integer batch2ExpectedResult = 9;
			Integer batch3ExpectedResult = 14;
			
			Map<List<Integer>, Integer> batch2ResultMap = new HashMap<>();
			batch2ResultMap.put(sequence1, batch1ExpectedResult);
			batch2ResultMap.put(sequence2, batch2ExpectedResult);
			batch2ResultMap.put(sequence3, batch3ExpectedResult);
			
			List<Result> results = processor.getResults();
			for (Result r : results) {
				Integer expectedResult = batch2ResultMap.get(r.getCodesAndArgumentsBatch());
				Integer actualResult = r.getResultValue();
				Assert.assertEquals(expectedResult, actualResult);
			}
		}
	}
	
	@Test
	public void testInstructions() {
		PpsProcessor processor = new PpsProcessor();
		processor.setDebugOn(true);
		
		// PUSH
		List<Integer> sequence1 = new ArrayList<>();
		sequence1.add(1);
		sequence1.add(100);
		sequence1.add(1);
		sequence1.add(200);
		processor.reset();
		processor.process(sequence1);
		Result result1 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(200), result1.getResultValue() );
		
		// POP
		List<Integer> sequence2 = new ArrayList<>();
		sequence2.add(1);
		sequence2.add(100);
		sequence2.add(1);
		sequence2.add(200);
		sequence2.add(2);
		processor.reset();
		processor.process(sequence2);
		Result result2 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(100), result2.getResultValue());
		
		// DUP
		List<Integer> sequence3 = new ArrayList<>();
		sequence3.add(1);
		sequence3.add(100);
		sequence3.add(1);
		sequence3.add(200);
		sequence3.add(3);
		processor.reset();
		processor.process(sequence3);
		Result result3 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(200), result3.getResultValue());
		
		// ADD
		List<Integer> sequence4 = new ArrayList<>();
		sequence4.add(1);
		sequence4.add(100);
		sequence4.add(1);
		sequence4.add(200);
		sequence4.add(4);
		processor.reset();
		processor.process(sequence4);
		Result result4 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(300), result4.getResultValue());
		
		// SUBTRACT
		List<Integer> sequence5 = new ArrayList<>();
		sequence5.add(1);
		sequence5.add(100);
		sequence5.add(1);
		sequence5.add(200);
		sequence5.add(5);
		processor.reset();
		processor.process(sequence5);
		Result result5 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(-100), result5.getResultValue());
		
		// MULTIPLY
		List<Integer> sequence6 = new ArrayList<>();
		sequence6.add(1);
		sequence6.add(4);
		sequence6.add(1);
		sequence6.add(200);
		sequence6.add(6);
		processor.reset();
		processor.process(sequence6);
		Result result6 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(800), result6.getResultValue());
		
		// DIVIDE
		List<Integer> sequence7 = new ArrayList<>();
		sequence7.add(1);
		sequence7.add(4);
		sequence7.add(1);
		sequence7.add(200);
		sequence7.add(7);
		processor.reset();
		processor.process(sequence7);
		Result result7 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(50), result7.getResultValue());
		
		// ASSIGN_ID
		List<Integer> sequence8 = new ArrayList<>();
		sequence8.add(1);
		sequence8.add(100);
		sequence8.add(8);
		sequence8.add(1);
		sequence8.add(500);
		processor.reset();
		processor.process(sequence8);
		Result result8 = processor.getResults().get(0);
		Assert.assertEquals(Integer.valueOf(500), result8.getResultValue());
		
		Integer taskResult = processor.getTaskResult(100);
		Assert.assertEquals(Integer.valueOf(500), taskResult);
		
		// PUSH_TASK
		List<Integer> sequence9 = new ArrayList<>();
		sequence9.add(1);
		sequence9.add(100);
		sequence9.add(8);
		sequence9.add(1);
		sequence9.add(500);
		processor.reset();
		processor.process(sequence9);
		
		List<Integer> sequence10 = new ArrayList<>();
		sequence10.add(1);
		sequence10.add(100);
		sequence10.add(9);
		processor.reset();
		processor.process(sequence10);
		
		List<Result> results = processor.getResults();
		for (Result r : results) {
			Integer actualResult = r.getResultValue();
			Assert.assertEquals(Integer.valueOf(500), actualResult);
		}
	}

}
