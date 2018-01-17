/*
 * @(#)FrontPanel.java			12 Jan 2014
 *
 */
package com.codecrocodile.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codecrocodile.main.Result;

/**
 * @author Chris Hatton
 */
@SuppressWarnings("serial")
public class FrontPanel extends JPanel {
	
	private Log log = LogFactory.getLog(FrontPanel.class);
	
	private FrontPanelModel frontPanelModel;
	
	private JCheckBox debugChk;
	
	private JTextArea inputTxtArea;
	
	private JButton resetBtn;
	
	private JButton submitBtn;
	
	private JButton cancelProcessingBtn;
	
	private JList<Result> resultList;

	
	/**
	 * Constructor
	 */
    public FrontPanel(FrontPanelModel frontPanelModel) {
    	this.frontPanelModel = frontPanelModel;
		this.setLayout(new BorderLayout());
    	this.setOpaque(true);
    	this.setBackground(new Color(0x66a1d2));
    	this.add(buildInputPanel(), BorderLayout.NORTH);
    	this.add(buildOutputPanel(), BorderLayout.CENTER);
    }
    
    private JPanel buildInputPanel() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setOpaque(false);
    	panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    	
    	JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	checkPanel.setOpaque(false);
    	debugChk = new JCheckBox("Debug On", true);
    	debugChk.setOpaque(false);
    	frontPanelModel.setDebugOn(true);
    	debugChk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frontPanelModel.setDebugOn(debugChk.isSelected());
			}
		});
    	checkPanel.add(debugChk);
    	
    	JPanel inputPanel = new JPanel(new BorderLayout());
    	inputPanel.setOpaque(false);
    	inputPanel.setBorder(BorderFactory.createTitledBorder("Input Code Sequence"));
    	inputTxtArea = new JTextArea(new CodeSequenceDocument());
    	inputTxtArea.setRows(10);
    	inputPanel.add(inputTxtArea, BorderLayout.NORTH);
    	
    	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	btnPanel.setOpaque(false);
    	resetBtn = new JButton("Reset");
    	resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTxtArea.setText("");
				resultList.setModel(new DefaultListModel<Result>());
			}
		});
    	submitBtn = new JButton("Submit");
    	submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetBtn.setEnabled(false);
				submitBtn.setEnabled(false);
				resultList.setModel(new DefaultListModel<Result>());
				
				String txtAreaContent = inputTxtArea.getText().trim();
				final List<List<Integer>> parsedInput = parseInput(txtAreaContent);
				
				Runnable r = new Runnable() {
					@Override
					public void run() {
						frontPanelModel.processCodeSequences(parsedInput);
						try {
							List<Result> results = frontPanelModel.getResults();
							final DefaultListModel<Result> listModel = new DefaultListModel<>();
							for (Result r : results) {
								listModel.addElement(r);
							}
							
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									resultList.setModel(listModel);
									resetBtn.setEnabled(true);
									submitBtn.setEnabled(true);
								}
							});
                        } catch (CancellationException e2) {
	                        log.info("Can't display results, processing was canceled.");
                        }
					}
				};
				ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
				newSingleThreadExecutor.execute(r);
			}
		});
    	cancelProcessingBtn = new JButton("Cancel Processing");
    	cancelProcessingBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frontPanelModel.cancelProcessing();
				resetBtn.setEnabled(true);
				submitBtn.setEnabled(true);
			}
		});
    	btnPanel.add(cancelProcessingBtn);
    	btnPanel.add(resetBtn);
    	btnPanel.add(submitBtn);

    	panel.add(checkPanel, BorderLayout.NORTH);
    	panel.add(inputPanel, BorderLayout.CENTER);
    	panel.add(btnPanel, BorderLayout.SOUTH);
    	
    	return panel;
    }
    
    private List<List<Integer>> parseInput(String txtAreaContent) {
    	List<List<Integer>> codeSequences = new ArrayList<>();
    	
    	String[] lineArrays = txtAreaContent.split("\n");
    	
    	List<Integer> codeSequence = null;
    	for (String s : lineArrays) {
    		codeSequence = new ArrayList<>();
    		String[] lineTokens= s.split("\\s");
    		for (String token : lineTokens) {
    			if (!token.trim().equals("")) {
    				codeSequence.add(Integer.parseInt(token));
    			}
     		}
    		
    		if (codeSequence.size() >  0) {
    			codeSequences.add(codeSequence);
    		}
    	}
    	
    	return codeSequences;
    }
    
    private JPanel buildOutputPanel() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setBorder(BorderFactory.createCompoundBorder(
    			BorderFactory.createEmptyBorder(15, 15, 15, 15), 
    			BorderFactory.createTitledBorder("Output Results")));
    	panel.setOpaque(false);
    	
    	resultList = new JList<>();
    	panel.add(resultList, BorderLayout.CENTER);
    	
    	return panel;
    }
    
    private class CodeSequenceDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;

            for ( int i = 0; i < chars.length; i++ ) {
            	if (chars[i] == '\n' || chars[i] == ' ' ) {
            		continue;
            	}
                try {
                    Integer.parseInt( String.valueOf( chars[i] ) );
                } catch ( NumberFormatException exc ) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
            	super.insertString( offs, new String( chars ), a );
            }
        }
    }

}
