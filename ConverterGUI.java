import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class ConverterGUI extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	
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
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Binary <-> Decimal Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new ConverterGUI());
		
		frame.pack();
		frame.setVisible(true);
	}
	
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
	
	public static void main(String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
