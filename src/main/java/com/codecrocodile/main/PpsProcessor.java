/*
 * @(#)PpsProcessor.java			10 Jan 2014
 *
 */
package com.codecrocodile.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codecrocodile.instruction.InstructionFactory;
import com.codecrocodile.instruction.InstructionFactoryIF;

/**
 * @author Chris Hatton
 */
public class PpsProcessor {
	
	private Log log = LogFactory.getLog(PpsProcessor.class);
	
	private final ReentrantLock lock = new ReentrantLock();
	
	private final Condition condition = lock.newCondition();
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	private InstructionFactoryIF instructionFactory = new InstructionFactory();
	
	private Map<Integer, Integer> taskResults = new HashMap<>();
	
	private boolean debugOn = false;
	
	private List<Future<PpsTask>> taskFutures = new ArrayList<>();
	
	
	public void process(List<Integer> codesAndArgumentsBatch) {
		log.info("Entering: process(List<Integer> codesAndArgumentsBatch)");
		
		PpsTask task = new PpsTask(this, codesAndArgumentsBatch);
		taskFutures.add(executorService.submit(task));
	}
	
	public void setTaskResult(Integer taskResultId, Integer taskResult) {
		lock.lock(); // wait until this thread gets the lock
		try {
			taskResults.put(taskResultId, taskResult);
		} finally {
			condition.signalAll(); // notify threads waiting for value to try again
			lock.unlock(); // releasing the lock so that other threads can get notifies
		} 
	}
	
	public Integer getTaskResult(Integer taskResultId) {
		lock.lock();

		try {
			while (taskResults.get(taskResultId) == null) {
				try {
					condition.await();
				} catch (InterruptedException e) {}
			}

			return taskResults.get(taskResultId);
		} finally {
			lock.unlock();
		}
	}
	
	public boolean isProccessingComplete() {
		boolean allTasksfinshed = true;
		
		for (Future<PpsTask> f : taskFutures) {
			if (f.isDone() == false) {
	            allTasksfinshed = false;
	            break;
            }
		}
		
		return allTasksfinshed;
	}
	
	public List<Result> getResults() {
		List<Result> resultList = new ArrayList<>();

		Result result = null;
		for (Future<PpsTask> future : taskFutures) {
			result = new Result();
			try {
				PpsTask ppsTask = future.get();
				result.setCodesAndArgumentsBatch(ppsTask.getCodesAndArgumentsBatch());
				result.setResultValue(ppsTask.getTaskResult());
				resultList.add(result);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		return resultList;
	}

	public void reset() {
		taskResults.clear();
		taskFutures.clear();
	}

	public void forceStop() {
		executorService.shutdownNow();
	}
	
	public void cancelAllTasks() {
		for (Future<PpsTask> future : taskFutures) {
			future.cancel(true);
		}
	}
	
	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public InstructionFactoryIF getInstructionFactory() {
		return instructionFactory;
	}

	public void setInstructionFactory(InstructionFactoryIF instructionFactory) {
		this.instructionFactory = instructionFactory;
	}

	public boolean isDebugOn() {
		return debugOn;
	}

	public void setDebugOn(boolean debugOn) {
		this.debugOn = debugOn;
	}

}
