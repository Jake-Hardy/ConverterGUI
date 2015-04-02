import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class ConverterGUI extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	
	/* This constructor create a new panel with a GridBagLayout using the super() method.  *
	 * Next, a JTextField is instantiated, and an actionListener is added to it. Then, a   *
	 * JTextArea is created, it is set to be un-editable, and a JScrollPane is added to it.*
	 * Some GridBagConstraints are created, but I don't fully understand these yet, so     *
	 * there's no documentation for these right now. textField and scrollPane are added to *
	 * the panel.																		   */
	public ConverterGUI() {
		super(new GridBagLayout());
		
		textField = new JTextField(20);
		textField.addActionListener(this);
		
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}
	
	/* This is the action that is performed when the actionListener on the text field fires.*
	 * The listener is activated when the user hits enter after typing in the textField.    *
	 * First, the number is retrieved from textField, and the number is parsed and stored in*
	 * inputNumInt. 																	    */
	public void actionPerformed(ActionEvent e) {
		String inputNum = textField.getText();
		String result;
		int inputNumInt = Integer.parseInt(inputNum);
		
		/* The exception handling doesn't seem to work as far as I can tell, but I have no  *
		 * clue why. First, the program checks to see whether the entered number is binary  *
		 * or decimal. If the number is a valid binary number, the convertToDecimal()       *
		 * function is called. If it isn't a binary number, convertToBinary() is called.    */
		try {
			if (isBinary(inputNumInt)) {
				result = String.valueOf(convertToDecimal(inputNumInt));
			
				textArea.append(inputNum + " is " + result + " in base-10." + newline);
				textField.selectAll();
			
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
			else {
				result = String.valueOf(convertToBinary(inputNumInt));
				
				textArea.append(inputNum + " is " + result + " in binary." + newline);
				textField.selectAll();
				
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		}
		
		/* For some reason, this block doesn't seem to run when it should. Need to fix this. */
		catch (NumberFormatException nfe) {
			textArea.append("Please enter a valid integer value." + newline);
			textArea.setCaretPosition(textArea.getDocument().getLength());
			System.err.println("Caught Exception " + nfe);
		}
		
		/* Empty textField. */
		finally {
			System.out.println("Reached finally block");
			textField.setText("");
		}
	}
	
	/* Create the frame for the GUI, set it to terminate the program when the window *
	 * is closed, and adds a new instance of ConverterGUI to it. The components are  *
	 * packed, and visibility is set to true. 										 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Binary <-> Decimal Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new ConverterGUI());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/* Check to see if the entered number is a binary number. */
	public static boolean isBinary(int number) {
		int numCopy = number;
		
		while (numCopy > 0) {
			if (numCopy % 10 != 1) {
				if (numCopy % 10 != 0) {
					return false;
				}
			}
			numCopy /= 10;
		}
		return true;
	}
	
	/* Check the right-most digit for a 1, and add 1 * 2^i to the total. The number is *
	 * divided by ten to strip the right-most digit and start over.                    */
	public static int convertToDecimal(int number) {
		int numCopy = number;
		int decimalTotal = 0;
		
		for (int i = 0; numCopy > 0; i++) {
			if (numCopy % 10 == 1) {
				decimalTotal += 1 * Math.pow(2, i);
			}
			numCopy /= 10;
		}
		
		return decimalTotal;
	}
	
	/* This could probably be done more neatly; I'll be working on this eventually. Right *
	 * now what it does is: An 8-value array is filled with 0s. Next, it determines which *
	 * bit-flags to turn on(change to 1), then the array is dumped in to a string, and    *
	 * that is parsed in to an int.														  */
	public static int convertToBinary(int number) {
		int numCopy = number;
		String [] binaryNumberArray = new String[8];
		String binaryNumberString = "";
		int binaryNumber = 0;
		
		for (int i = 0; i < 8; i++) {
			binaryNumberArray[i] = "0";
		}
		
		
		while (numCopy > 0) {
			if (numCopy >= 128) {
				binaryNumberArray[0] = "1";
				numCopy -= 128;
			}
			if (numCopy >= 64) {
				binaryNumberArray[1] = "1";
				numCopy -= 64;
			}
			if (numCopy >= 32) {
				binaryNumberArray[2] = "1";
				numCopy -= 32;
			}
			if (numCopy >= 16) {
				binaryNumberArray[3] = "1";
				numCopy -= 16;
			}
			if (numCopy >= 8) {
				binaryNumberArray[4] = "1";
				numCopy -= 8;
			}
			if (numCopy >= 4) {
				binaryNumberArray[5] = "1";
				numCopy -= 4;
			}
			if (numCopy >= 2) {
				binaryNumberArray[6] = "1";
				numCopy -= 2;
			}
			if (numCopy > 0) {
				binaryNumberArray[7] = "1";
				numCopy -= 1;
			}
		}
		
		for (int i = 0; i < 8; i++) {
			binaryNumberString += binaryNumberArray[i];
		}
		
		binaryNumber = Integer.parseInt(binaryNumberString);
		return binaryNumber;
	}
	
	/* Run createAndShowGUI when (I assume) the program finishes loading. */
	public static void main(String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
