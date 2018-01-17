/*
 * @(#)FrontPanelModel.java			12 Jan 2014
 *
 */
package com.codecrocodile.gui;

import java.util.List;
import java.util.Observable;

import com.codecrocodile.main.PpsProcessor;
import com.codecrocodile.main.Result;

/**
 * @author Chris Hatton
 */
public class FrontPanelModel extends Observable {
	
	private PpsProcessor ppsProcessor;

	
	/**
	 * Constructor
	 */
    public FrontPanelModel(PpsProcessor ppsProcessor) {
		this.ppsProcessor = ppsProcessor;
    }

    void setDebugOn(boolean debugOn) {
    	ppsProcessor.setDebugOn(debugOn);
    }
    
    void processCodeSequences(List<List<Integer>> codeSequences) {
    	ppsProcessor.reset();
    	for (List<Integer> codeSequence : codeSequences) {
    		ppsProcessor.process(codeSequence);
    	}
    }

    List<Result> getResults() {
    	return ppsProcessor.getResults();
    }
    
    void cancelProcessing() {
    	ppsProcessor.cancelAllTasks();
    }

}
