package com.futaba.ePO;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DirectoryOpener {

	private String filename;

	public DirectoryOpener()

	{	


		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //make it look pretty
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose the directory with your contents");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showOpenDialog(getParent());
		File directory = fileChooser.getSelectedFile();	

		try {
			filename = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Component getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName()
	{
		return this.filename;
	}


}
