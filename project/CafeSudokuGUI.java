package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;


public class CafeSudokuGUI{

	private static Vector<JComboBox> _numComponents;
	//private static Vector<JButton> _numButtons;
	
	private SudokuPuzzle _currentPuzzle;
	
	public CafeSudokuGUI(SudokuPuzzle x){
		this._currentPuzzle = x;
		_numComponents = new Vector<JComboBox>();
		setUpGUI();
		
	}
	public CafeSudokuGUI(){
		
		_numComponents = new Vector<JComboBox>();
		this._currentPuzzle = new SudokuPuzzle();
		setUpGUI();
		//_numButtons = new Vector<JButton>();

	}
	
	private void setUpGUI(){
		JFrame mainFrame = new JFrame("Café Sudoku");

		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 17, 12));

		JPanel puzzlePanel = new JPanel();
		puzzlePanel.setBorder(BorderFactory.createEmptyBorder(0,0,6,0));
		
		JButton openBtn = new JButton("Open...");
		JButton solveBtn = new JButton("Solve");
		
		openBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {handleFile();}
		});
		solveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {_currentPuzzle.solve();}
		});
		
		JPanel buttonRow = new JPanel();
		
		buttonRow.setLayout(new BoxLayout (buttonRow, BoxLayout.X_AXIS));
		
		Component buttonSpace = Box.createRigidArea(new Dimension(6,6));
		
		buttonRow.add(openBtn);
		buttonRow.add(buttonSpace);
		buttonRow.add(solveBtn);
		
		puzzlePanel.setLayout(new GridLayout(0,11,6,6));
		
		Vector<Integer> possValues = new Vector<Integer>();
		
		for (int i=0; i<10; ++i){
			possValues.add(i);
		}
		
		//TODO: Add Cell's JComboBox to GUI here, check Cells row and column for border
		for (int i=0; i < this._currentPuzzle.get_numCells(); ++i){
			Cell a = this._currentPuzzle.get_puzzleCell(i);
			
			Integer row = this._currentPuzzle.get_puzzleCell(i).get_rownum();
			Integer col = this._currentPuzzle.get_puzzleCell(i).get_colnum();
			
			puzzlePanel.add(a.get_comboBox());
			/*if (col % 3 == 2 && row % 3 == 2){
				puzzlePanel.add(Box.createVerticalStrut(30));
			}*/
			if (col % 3 == 2 && col < 7){
				puzzlePanel.add(Box.createRigidArea(new Dimension(1,1)));
			}
			if (row %3 == 2 && col == 8 && row < 7){
				for (int k =0; k<11; ++k){
					puzzlePanel.add(Box.createRigidArea(new Dimension(1,1)));
				}
			}
		}
		
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
		
		//TODO: Add this to a separate, Help, Window
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		try{
			editorPane.setPage("file:///Users/mark/Documents/index.html");
		}
		catch (IOException e){
			System.err.println("Error loading page!");
		}
		
		JPanel cMain = new JPanel();
		cMain.setLayout(new BoxLayout(cMain, BoxLayout.X_AXIS));		
		
		cMain.add(puzzlePanel);
		cMain.add(editorPane);
		borderPanel.add(cMain);
		borderPanel.add(buttonRow, BorderLayout.SOUTH);
		
		mainFrame.add(borderPanel);
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        //mainFrame.setMinimumSize(new Dimension(613,354));
        mainFrame.setResizable(true);
        
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
	
	public SudokuPuzzle get_currentPuzzle(){
		return _currentPuzzle;
	}
	public void set_currentPuzzle(SudokuPuzzle x){
		this._currentPuzzle = x;
		return;
	}
	
	//TODO: Change this back to private
	public void handleFile(){
		
		JDialog fileDialog = new JDialog();
		
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fileDialog);
		if (returnVal == JFileChooser.APPROVE_OPTION){//handle file
			System.out.println("File chosen");
			File chosenFile = fc.getSelectedFile();
			if (!chosenFile.canRead());
			{
				//Throw can't read error, return
			}

			System.out.println(chosenFile.getAbsolutePath());
			//Read in File
			this.readFile(chosenFile.getAbsolutePath());
			
			
		}
		else{
			System.out.println("File not chosen");
			//This forced exit only needed before open button set up 
			//System.exit(5);
		}
	}
	
	private void readFile(String filename){
		try{
			FileReader fin = new FileReader(filename);
			BufferedReader in = new BufferedReader(fin);
			
			Integer currentRow = 0;
			
			while (in.ready() && currentRow < 9){
				String text = in.readLine();
				_currentPuzzle.parseInputLine(text, currentRow);

				//Increment current row
				currentRow = currentRow + 1;
			}
		}
		catch (Exception e){
			System.err.print(e);
		}
		
	}
}
