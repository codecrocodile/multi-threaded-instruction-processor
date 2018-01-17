/*
 * @(#)MainFrame.java			12 Jan 2014
 *
 */
package com.codecrocodile.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.codecrocodile.main.PpsProcessor;

/**
 * @author Chris Hatton
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private WindowListener windowListener;
	
	private PpsProcessor ppsProcessor;
	
	private FrontPanel frontPanel;
	
	private FrontPanelModel frontPanelModel;
	

	public MainFrame() {
		super("PPS Prcoessor");
		this.setSize(800, 600);
		
		this.windowListener = new WindowListener();
		this.addWindowListener(windowListener);
		this.ppsProcessor = new PpsProcessor();
		this.frontPanelModel = new FrontPanelModel(ppsProcessor);
		this.frontPanel = new FrontPanel(frontPanelModel);
		this.add(frontPanel, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			exitApplication();
		}
	}

	private void exitApplication() {
		ppsProcessor.forceStop();
		this.dispose();
		System.exit(0);
	}
}
