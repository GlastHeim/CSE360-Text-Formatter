/*
	Processes formatting commands
*/

import java.util.*;
import java.io.*;
// End needed

public class Formatter{

	// Formatting defaults
	private int lineLen = 80;
	private int align = 0;	// 0 = left, 1 = right, 2 = centered
	private boolean eqSpacing = false;
	private boolean textWrap = false;
	private boolean singleSpacing = true;	// false = double-spacing
	private boolean title = false;
	private int paragraph = 0;
	private int blankLn = 0;
	private boolean columns = false;	// true = 2 columns, false = 1 column
	
	// File input/reading
	private ArrayList<String> inputFileAL = new ArrayList<String>();
	private ArrayList<String> errorLog = new ArrayList<String>();
	
	// Default constructor
	public Formatter(ArrayList<String> inputAL) {
		for (int i = 0; i < inputAL.size(); i++) {
			inputFileAL.add(inputAL.get(i));
		}
	}
	
	// setFormattingVals - Uses switch case to determine which formatting
	//	defaults to change in accordance with command located in given
	//	element in the inputFileAL
	public void setFormattingVals() {
		for (int i = 0; i < inputFileAL.size(); i++) {
			// Checking if String is a formatting command
			if (inputFileAL.get(i).charAt(0) == '-'){
				String command = inputFileAL.get(i);
				String commandSub = command.substring(1, (command.length() - 1));
				
				// Checking which formatting command should be adjusted
				switch(commandSub.charAt(0)) {
				// Line length
				case 'n':
					int newVal;
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 80;	// default value
						errorLog.add("Invalid line length: " + commandSub);
					}
					lineLen = newVal;
					break;
				// Right alignment
				case 'r':
					align = 1;
					break;
				// Left alignment
				case 'l':
					align = 0;
					break;
				// Center alignment
				case 'c':
					align = 2;
					break;
				// Equally-spaced words
				case 'e':
					eqSpacing = true;
					break;
				// Text wrapping - on or off
				case 'w':
					if (commandSub.charAt(1) == '+') {
						textWrap = true;
					}else {
						textWrap = false;
					}
					break;
				// Single-spacing
				case 's':
					singleSpacing = true;
					break;
				// Double-spacing
				case 'd':
					singleSpacing = false;
					break;
				// Title
				case 't':
					title = true;
					break;
				// Paragraph
				case 'p':
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 0;	// default value
						errorLog.add("Invalid paragraph spacing: " + commandSub);
					}
					paragraph = newVal;
					break;
				// Blank lines
				case 'b':
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 0;	// default value
						errorLog.add("Invalid number of blank lines: " + commandSub);
					}
					blankLn = newVal;
					break;
				// Set columns
				case 'a':
					try {
						newVal = Integer.parseInt(commandSub.substring(1, (commandSub.length() - 1)));
					}catch(NumberFormatException e) {
						newVal = 1;	// default value
						errorLog.add("Invalid number of columns: " + commandSub);
					}
					if (newVal == 2) {
						columns = true;
					}else {
						columns = false;
					}
					break;
				// Invalid command
				default:
					errorLog.add("Invalid command: " + commandSub);
					break;
				}
			}else {}	// String is not a formatting command
		}
		
	}// End of setFormattingVals()
	
	
	
}// End of Formatter class