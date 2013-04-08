package com.futaba.ePO;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GUI extends JFrame
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 700; //initial window dimensions
	private static final int HEIGHT = 300;

	private JTextField inputTF, outputTF, dateTF; //text fields
	private JButton inputB, exitB, outputB, pdfB; //buttons
	private JLabel dateL; //labels

	//Button handlers:
	private InputButtonHandler ibHandler;
	private ExitButtonHandler ebHandler;
	private OutputButtonHandler obHandler;
	private PDFButtonHandler pdfHandler;

	public static String inputdirectoryname, outputdirectoryname, dateN; //appears in pdfscraper.java


	public GUI()
	{
		
	    try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //make it look pretty
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		inputTF = new JTextField(10);
		outputTF = new JTextField(10);
		dateTF = new JTextField(10);

		//Specify handlers for each button and add (register) ActionListeners to each button.
		inputB = new JButton("Input From");
		ibHandler = new InputButtonHandler();
		inputB.addActionListener(ibHandler);

		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);

		outputB = new JButton("Output To");
		obHandler = new OutputButtonHandler();
		outputB.addActionListener(obHandler);

		pdfB = new JButton("Run PDF Program");
		pdfHandler = new PDFButtonHandler();
		pdfB.addActionListener(pdfHandler);
		
		dateL = new JLabel("Please Enter Today's Date: (in MM-DD-YYYY format)", SwingConstants.CENTER);
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		dateTF.setText(dateFormat.format(date));

		setTitle("Toyota ePO Automation Program");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(4, 2));

		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(inputB);
		pane.add(inputTF);
		pane.add(outputB);
		pane.add(outputTF);

		pane.add(dateL);
		pane.add(dateTF);

		pane.add(pdfB);
		pane.add(exitB);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public class InputButtonHandler implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
		{

			DirectoryOpener inputopen = new DirectoryOpener();
			inputdirectoryname = inputopen.getName();
			inputTF.setText(inputdirectoryname);
			
			outputdirectoryname = inputopen.getName();
			outputTF.setText(outputdirectoryname);

		}

	}

	public class OutputButtonHandler implements ActionListener
	{		

		public void actionPerformed(ActionEvent e)
		{	
			DirectoryOpener outputopen = new DirectoryOpener();
			outputdirectoryname = outputopen.getName();
			outputTF.setText(outputdirectoryname);
		}

	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	public class PDFButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			long time_start = System.nanoTime();
			
			dateN = dateTF.getText();
			
				try {
					new Master_Logic();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			long time_end = System.nanoTime();
			long total_time = time_end - time_start;
			
			long totalTimeSeconds = total_time/1000000000;
			
			long displayMinutes =  totalTimeSeconds/60;
			long displaySeconds =  (totalTimeSeconds-(displayMinutes*60));
			
			JOptionPane.showMessageDialog(null, Master_Logic.numberoffiles + " files have been processed in " + displayMinutes + " minutes and " + displaySeconds + " seconds."); //let user know that something has actually happened, and how many files were processed
		}
	}

	public static void main(String[] args)
	{
		new GUI();
	}

}
