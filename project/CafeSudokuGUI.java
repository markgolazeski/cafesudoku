package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.*;


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
		
		//TODO:Set up Menu
		JMenuBar menubar = new JMenuBar();
		mainFrame.setJMenuBar(menubar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(70);
		menubar.add(fileMenu);
		
		JMenu helpMenu = new JMenu("Help");
		menubar.add(helpMenu);
		
		//TODO:Add puzzle margins
		JMenuItem newPuzzle = new JMenuItem("New");
		newPuzzle.setMnemonic(78);
		newPuzzle.setAccelerator(KeyStroke.getKeyStroke('N', 2));
		newPuzzle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {newPuzzle();}
		});
		fileMenu.add(newPuzzle);
		
		JMenuItem open = new JMenuItem("Open...");
		open.setMnemonic(111);
		open.setAccelerator(KeyStroke.getKeyStroke('O', 2));
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {openFile();}
		});
		fileMenu.add(open);
		fileMenu.add(new JSeparator());
		
		JMenuItem saveAs = new JMenuItem("Save As...");
		saveAs.setMnemonic(123);
		saveAs.setAccelerator(KeyStroke.getKeyStroke('S', 2));
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){saveAsPuzzle();}
		});
		fileMenu.add(saveAs);
		
		JMenuItem help = new JMenuItem("Help Contents");
		help.setMnemonic(72);
		helpMenu.add(help);
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {displayHelpWindow();}
		});

		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 17, 12));

		JPanel puzzlePanel = new JPanel();
		puzzlePanel.setBorder(BorderFactory.createEmptyBorder(0,0,6,0));
		
		JButton openBtn = new JButton("Open...");
		JButton solveBtn = new JButton("Solve");
		JButton validBtn = new JButton("Validate");
		
		openBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {openFile();}
		});
		solveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_currentPuzzle.setKeepSolving(true);
				_currentPuzzle.solve();}
		});
		validBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!_currentPuzzle.isValid()){
					//TODO:Update to mention they are highlighted, once implemented
					displayErrorMessage("This Puzzle is not valid.\nThe first conflict found is highlighted.");
				}
			}
		});
		
		JPanel buttonRow = new JPanel();
		
		buttonRow.setLayout(new BoxLayout (buttonRow, BoxLayout.X_AXIS));
		
		Component buttonSpace = Box.createRigidArea(new Dimension(6,6));
		
		buttonRow.add(openBtn);
		buttonRow.add(buttonSpace);
		buttonRow.add(solveBtn);
		buttonRow.add(buttonSpace);
		buttonRow.add(validBtn);
		
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
		
		borderPanel.add(puzzlePanel);
		borderPanel.add(buttonRow, BorderLayout.SOUTH);
		
		mainFrame.add(borderPanel);
		
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        //mainFrame.setMinimumSize(new Dimension(613,354));
        mainFrame.setResizable(false);
        
	}
	
	private static void displayHelpWindow(){
		JDialog helpFrame = new JDialog();
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		try{
			//TODO: Make this a relative path as opposed to an absolute one
			editorPane.setPage("file:///Users/mark/Documents/index.html");
			//editorPane.setPage("http://www.pages.drexel.edu/~mjg722/startpage.html");
		}
		catch (IOException e){
			displayErrorMessage("Error loading page!");
		}
		
		JPanel cMain = new JPanel();
		cMain.add(editorPane);
		helpFrame.add(cMain);
		
        helpFrame.setSize(418, 466);
        //TODO:Set up help window to display next to mainframe instead of on top of it
        //helpFrame.setLocationRelativeTo(mainFrame);
        helpFrame.setVisible(true);
        helpFrame.setResizable(true);
	}
	
	public static String displayStepSolve(Integer guess){
		
		String message = "Removing possible value " + guess;
		
		Object[] options = {"Next Step", "Finish Solving", "Stop Solving"};		
		
		JOptionPane test = new JOptionPane();
		int n = JOptionPane.showOptionDialog(null, message, "Continue Stepping?", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				options, options[0]);
		
		if(n == JOptionPane.YES_OPTION){
			//Keep on stepping
			return "YES";
		}
		if(n == JOptionPane.NO_OPTION){
			//Stop stepping, go solve
			return "NO";
		}
		if(n == JOptionPane.CANCEL_OPTION){
			//TODO: Figure out way to abort solve from here
			//Stop stepping
			System.out.println("CANCEL");
			return "CANCEL";
		}

		return "YES";
	}
	
	public static void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
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
	
	public void newPuzzle(){
		//TODO:Check if puzzle is blank
		//TODO:If it isn't prompt to save
	}
	public void saveAsPuzzle(){
		JDialog fileDialog = new JDialog();
		
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fileDialog);
		if (returnVal == JFileChooser.APPROVE_OPTION){//handle file
			//System.out.println("File chosen");
			File chosenFile = fc.getSelectedFile();
			if (!chosenFile.canRead()){
				//Throw can't read error, return
			}
			else{
				//System.out.println("File not chosen");
			}

			//displayErrorMessage(chosenFile.getAbsolutePath());
			//WriteFile
			this.writeFile(chosenFile.getAbsolutePath());
			
			
		}
	}
	
	public void openFile(){
		
		//TODO:Reset puzzle to be able to load in a new one without problems
		//this._currentPuzzle.reset();
		handleFile();
	}
	
	//TODO: Change this back to private 
	public void handleFile(){
		
		JDialog fileDialog = new JDialog();
		
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(fileDialog);
		if (returnVal == JFileChooser.APPROVE_OPTION){//handle file
			//System.out.println("File chosen");
			File chosenFile = fc.getSelectedFile();
			if (!chosenFile.canRead()){
				displayErrorMessage("Can't read file.\nMake sure the selected file is readable");
				//Throw can't read error, return
			}

			//displayErrorMessage(chosenFile.getAbsolutePath());
			//Read in File
			this.readFile(chosenFile.getAbsolutePath());
			
			
		}
		else{
			//System.out.println("File not chosen");
		}
	}
	
	private void readFile(String filename){
		//Watches file input validity, stops if messed up
		boolean stillGoodInput = true;
		try{
			FileReader fin = new FileReader(filename);
			BufferedReader in = new BufferedReader(fin);
			Integer currentRow = 0;

			while (in.ready() && currentRow < 9 && stillGoodInput){
				String text = in.readLine();
				stillGoodInput = _currentPuzzle.parseInputLine(text, currentRow);

				//Increment current row
				currentRow = currentRow + 1;
			}
			in.close();
			fin.close();
		}
		catch (Exception e){
			displayErrorMessage("Error reading file.");
		}
		
	}
	
	private void writeFile(String filename){
		//Get puzzle to output row by row and write it
		try{
			if(!filename.toLowerCase().endsWith(".txt")){
				filename = filename + ".txt";
			}
			BufferedWriter fout = new	BufferedWriter(new FileWriter(filename));
			String toWrite = "";
			for (int i=0; i<9;++i){
				toWrite = this._currentPuzzle.get_rowOutput(i);
				fout.write(toWrite);
			}
			
			fout.close();
		}
		catch(IOException e){
			displayErrorMessage("Error writing to file.\nMake sure you can write to the file.");
		}
		
	}
	
	public SudokuPuzzle get_currentPuzzle(){
		return _currentPuzzle;
	}
	public void set_currentPuzzle(SudokuPuzzle x){
		this._currentPuzzle = x;
		return;
	}
}
