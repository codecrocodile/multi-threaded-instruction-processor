/*
 * @(#)Start.java			12 Jan 2014
 *
 */
package com.codecrocodile.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Chris Hatton
 */
public class Start {
	
	private static Log log = LogFactory.getLog(Start.class);
	
	public static void main(String[] args) { 
		initLookAndFeel();
		createAndShowGUI();
    }
	
	private static void initLookAndFeel() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			log.error("UnsupportedLookAndFeelException");
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException");
		} catch (InstantiationException e) {
			log.error("InstantiationException");
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException");
		}
	} 

	private static void createAndShowGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}

}
