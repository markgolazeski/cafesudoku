package project;
import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

public class CafeSudokuGUI {

	private static Vector<JButton> _numComponents;
	
	public CafeSudokuGUI(){
		
		_numComponents = new Vector<JButton>();
		JFrame mainFrame = new JFrame("Café Sudoku");
		
		JPanel puzzlePanel = new JPanel();
		
		puzzlePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 20, 12));
		puzzlePanel.setLayout(new GridLayout(9,9));
		
		for(int i = 0; i < 9; ++i){
			for(int j=0; j<9; ++j){
				//% commands are being used for setting up borders
				JButton a = new JButton(new Integer(i%3).toString() + ", " + new Integer(j%3).toString());
				//a.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				puzzlePanel.add(a);
				_numComponents.add(a);
			}
		}
		
		
		
		mainFrame.add(puzzlePanel);
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
	}
	
	private void updateFields(int cellNumber, int val){
		//Set requested button value to value being passed in.
		_numComponents.get(cellNumber).setText(new Integer(val).toString());
	}
	
	public void syncPuzzleGUI(SudokuPuzzle puzzle){
		for(int i = 0; i<81; ++i){
			this.updateFields(i, puzzle.get_puzzleCells(i).get_finalval());
			
		}
	}
}
