/*
 * @(#)Result.java			11 Jan 2014
 *
 */
package com.codecrocodile.main;

import java.util.List;

/**
 * @author Chris Hatton
 */
public class Result {
	
	private List<Integer> codesAndArgumentsBatch;
	
	private Integer resultValue;
	

	public List<Integer> getCodesAndArgumentsBatch() {
		return codesAndArgumentsBatch;
	}

	public void setCodesAndArgumentsBatch(List<Integer> codesAndArgumentsBatch) {
		this.codesAndArgumentsBatch = codesAndArgumentsBatch;
	}

	public Integer getResultValue() {
		return resultValue;
	}

	public void setResultValue(Integer resultValue) {
		this.resultValue = resultValue;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    StringBuilder resultDescription = new StringBuilder();
	    
	    resultDescription.append("Code sequence = ");
	    
	    for (Integer codeOrArgument : codesAndArgumentsBatch) {
	    	resultDescription.append(codeOrArgument + " ");
	    }
	    
	    resultDescription.append(" ---- Result = " + resultValue);
	    
	    return resultDescription.toString();
	}

}
