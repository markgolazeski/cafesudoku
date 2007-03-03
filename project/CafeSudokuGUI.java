package project;
import java.awt.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;


public class CafeSudokuGUI extends CafeSudoku{

	private static Vector<JComboBox> _numComponents;
	//private static Vector<JButton> _numButtons;
	
	public CafeSudokuGUI(){
		
		_numComponents = new Vector<JComboBox>();
		//_numButtons = new Vector<JButton>();
		JFrame mainFrame = new JFrame("Café Sudoku");
		
		JPanel puzzlePanel = new JPanel();
		
		puzzlePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 17, 12));
		puzzlePanel.setLayout(new GridLayout(9,9));
		
		Vector<Integer> possValues = new Vector<Integer>();
		
		for (int i=0; i<10; ++i){
			possValues.add(i);
		}
		///*
		for(int i = 0; i < 9; ++i){
			for(int j=0; j<9; ++j){
				Integer rghtBorder = 0;
				Integer dwnBorder = 0;
				
				if (j % 3 == 2){ //Need to put in right border
					rghtBorder = 12;
				}
				else{
					rghtBorder = 0;
				}
				if (i % 3 == 2){//Need to put in bottum border
					dwnBorder = 12;
				}
				else{
					//System.out.println("DownBorder Changed to 0");
					dwnBorder = 0;
				}
					
				//% commands are being used for setting up borders
				JComboBox a = new JComboBox(possValues);
				//a.setBorder(BorderFactory.createEmptyBorder(0, 0, dwnBorder, rghtBorder));
				
				//if()
				
				puzzlePanel.add(a);
				_numComponents.add(a);
			}
		} //*/
		
		/*
		for(int i = 0; i < 9; ++i){
			for(int j=0; j<9; ++j){
				//% commands are being used for setting up borders
				JButton a = new JButton(new Integer(i%3).toString() + ", " + new Integer(j%3).toString());
				//a.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				puzzlePanel.add(a);
				_numButtons.add(a);
				
			}
		}//*/
		

		
		mainFrame.add(puzzlePanel);
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        
       handleFile();
	}
	
	private void updateFields(int cellNumber, int val){
		//Set requested button value to value being passed in.
		//_numComponents.get(cellNumber).setText(new Integer(val).toString());
		_numComponents.get(cellNumber).setSelectedIndex(val);
	}
	
	public void syncPuzzleGUI(SudokuPuzzle puzzle){
		for(int i = 0; i<81; ++i){
			this.updateFields(i, puzzle.get_puzzleCell(i).get_finalval());
			
		}
	}
	
	private void handleFile(){
		
		JDialog fileDialog = new JDialog();
		
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fileDialog);
		if (returnVal == JFileChooser.APPROVE_OPTION){//handle file
			System.out.println("File chosen");
			File chosenFile = fc.getSelectedFile();
			System.out.println(chosenFile.getAbsolutePath());
			if (!chosenFile.canRead());
			{
				//Throw can't read error, return
			}
			
			//Read in File
			this.readFile();
			
			
		}
		else{
			System.out.println("File not chosen");
		}
	}
	
	private void readFile(){
		
	}
}
