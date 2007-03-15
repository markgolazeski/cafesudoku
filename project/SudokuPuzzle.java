package project;

import java.util.Vector;

public class SudokuPuzzle {

	private Vector<Cell> _allPuzzleCells;
	
	/*	These Vectors contain a Vector for each row, column, or grid.
		Each row, column, or grid Vector contains an Integer that
		is an index to the _allPuzzleCells Vector.
	*/
	private Vector<Vector <Integer>> _rows;
	private Vector<Vector <Integer>> _cols;
	private Vector<Vector <Integer>> _grids;
	
	private boolean stepPref = true;
	private boolean alwaysCheckPref = true;
	private boolean keepSolving = true; 
	
	private String _filename = "";
	
	public SudokuPuzzle(){
		//Initialize Vectors for Row/Columns/Grids
		this._rows = new Vector<Vector <Integer>>();
		this._cols = new Vector<Vector <Integer>>();
		this._grids = new Vector<Vector <Integer>>();
		//Add 9 slots to each, since there are 9 rows etc.
		for (int i=0; i<9; ++i){
			Vector<Integer> a = new Vector<Integer>();
			Vector<Integer> b = new Vector<Integer>();
			Vector<Integer> c = new Vector<Integer>();
			this._rows.add(a);
			this._cols.add(b);
			this._grids.add(c);
		}
		
		
		
		System.out.println("Creating SudokuPuzzle()");
		_allPuzzleCells = new Vector<Cell>();
		for (int i = 0; i < 9; ++i){
			for (int j = 0; j < 9; ++j){
				Cell a = new Cell(i,j);
				a.set_finalval(new Integer(0));
				this._allPuzzleCells.add(a);
				
				//add cell number (length of all puzzle cells - 1)
				//to appropiate row/column/grid vectors
				Integer number = this._allPuzzleCells.size() - 1;

				//System.out.println("Adding " + number + " to " + i);
				this._rows.get(i).add(number);
				this._cols.get(j).add(number);
				this._grids.get(a.get_gridnum()).add(number);
			}
		}
		
		
		System.out.println("Puzzle Created!");
		//System.out.println("rows: " + this._rows.toString());
		//System.out.println("cols: " + this._cols.toString());
		//System.out.println("grids: " + this._grids.toString());
		
		/*for (int i=27; i<81; ++i){
			System.out.println("Cell: " + i + " row: " + this._allPuzzleCells.get(i).get_rownum() + " col: " + this._allPuzzleCells.get(i).get_colnum() + " grid: " + this._allPuzzleCells.get(i).get_gridnum());
		}*/
		
		/*for (int i=0; i<9;++i){
			System.out.println("row(0),spot(" + i +"): " + this._rows.elementAt(0).get(i).toString());
		}*/
	}
	
	public void reset(){
		for(int i =0; i<this._allPuzzleCells.size(); ++i){
			Cell tmp = this._allPuzzleCells.get(i);
/*			tmp.set_finalval(0);
			for (int j=1; j<10; ++j){
				tmp.remove_possVal(j);
			}*/
			tmp.initializePossValues();
			//tmp.remove_possVal(0);
		}
		
	}
	
	//TODO:figure out a way to consolidate this with the one in the GUI class
	/*private void displayErrorMessage(String message){
		 
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}*/
	
	public boolean isFinished(Integer x){
		if(x < this._allPuzzleCells.size()){
			return false;
		}
		return true;
	}
	public void reset_bgColor(){
		Vector<Integer> currentVector = new Vector<Integer>();
		for (int i=0; i<27; ++i){
			if (i < 9){
				currentVector = this._rows.get(i);
			}
			else if (i < 18){
				//System.out.println("Less than 18: " + i % 9);
				currentVector = this._cols.get(i % 9);
			}
			else{
				currentVector = this._grids.get(i % 9);
			}
			for(int w=0; w< currentVector.size(); ++w){
				this._allPuzzleCells.get(currentVector.get(w)).change_bgcolor("WHITE");
			}
		}
	}
	
	//Checks if a puzzle has valid entries
	//TODO: Highlight bad value(s)
	public boolean isValid(){
		//First reset bg colors
		reset_bgColor();
		String inCurrently = "";
		Integer count = 0;
		Integer compareValue = 0;
		Vector<Integer> currentVector = new Vector<Integer>();
		//Upto 9 times check rows
		//Upto 18 times check cols
		//Upto 27 times check grids
		for (int i=0; i<27; ++i){
			if (i < 9){
				currentVector = this._rows.get(i);
				inCurrently = "row";
			}
			else if (i < 18){
				//System.out.println("Less than 18: " + i % 9);
				currentVector = this._cols.get(i % 9);
				inCurrently = "column";
			}
			else{
				currentVector = this._grids.get(i % 9);
				inCurrently = "grid";
			}
			for (int j=1; j<10; ++j){
				//1 to 9, since those are the values we care about
				count = 0;				
				for (int k=0; k<9; ++k){
					compareValue = this._allPuzzleCells.get(currentVector.get(k)).get_finalval();
					//System.out.println("compare: " + compareValue + " k: " + j);
					if(compareValue == j){
						count = count + 1;
					}
					//Duplicate in the row
					if(count > 1){
						//System.out.println("There is a conflict in " + inCurrently + " " + (i%9 + 1) + ".");
						for(int w=0; w< currentVector.size(); ++w){
							this._allPuzzleCells.get(currentVector.get(w)).change_bgcolor("RED");
						}
						return false;
					}
				}
				//System.out.println("count: " + count);
				for(int w=0; w< currentVector.size(); ++w){
					this._allPuzzleCells.get(currentVector.get(w)).change_bgcolor("WHITE");
				}
			}
		}
		
		return true;
	}
	
	//General solving
	public void solve(){
		if (!this.isValid()){
			CafeSudokuGUI.displayErrorMessage("Can't start solving: A conflict was found.\nThe first conflict found is highlighted.");
			return;
		}
		Integer startFilled = getFinishedCells();
		if(isFinished(startFilled)){
			CafeSudokuGUI.displayErrorMessage("Can't start solving:\nThis puzzle is already correctly solved.");
			return;
		}
		System.out.println("The Puzzle is Starting with " + startFilled + " Cells Filled in.");
		//Puzzle is set up ok and not full
		//Begin solving with stage 1
		
		//keepSolving not needed for only one stage
		//boolean keepSolving = true; 
		boolean tryStage1 = true;
		Integer finishedCells;
		//TODO: set up booleans and subsequent
		//while (keepSolving){
			//keepSolving = false;//run it once
			while(tryStage1 && isKeepSolving()){
				tryStage1 = runStage1();
				if (!tryStage1){
					//Try a second run through if it doesn't work once
					tryStage1 = runStage1();
				}
			}
			
			//Stage1 finished
			
			finishedCells = getFinishedCells();
			//System.out.println(this._allPuzzleCells.size());
			boolean endvalid = this.isValid();
			if(finishedCells == this._allPuzzleCells.size() && endvalid){
				//keepSolving = false;
				System.out.println("Solved!");
			}
			else if(!endvalid){
				CafeSudokuGUI.displayErrorMessage("There's a conflict in the ending puzzle.");
			}
			else{
				//All Stages fail, report unsolvable
				System.out.println("Cells solved: " + finishedCells);	
				CafeSudokuGUI.displayErrorMessage("Puzzle wasn't successfully solved.\n" + finishedCells + " out of 81 cells were filled in successfully.");
			}
			
		//}
		
		
	}
	
	public Integer getFinishedCells(){
		Integer count = 0;
		for(int i = 0; i< this._allPuzzleCells.size(); ++i){
			if(this._allPuzzleCells.get(i).get_finalval() > 0){
				count = count + 1;
			}
		}
		return count;
	}
	
	public boolean runStage1(){
		
		Integer startCellsDone = this.getFinishedCells();
		Integer endCellsDone = 0;
		Integer currentFinalValue = 0;
		for (int i=0;i<this._allPuzzleCells.size(); ++i){
			if(!isKeepSolving()){
				//System.out.println("returning false");
				return false;
			}
			//Allow user to abort solving
			Cell currentCell = this._allPuzzleCells.get(i);
			//System.out.print("Cell: " + i + " vals: " + currentCell.num_possVal() + "\n");
			currentFinalValue = currentCell.get_finalval();
			
			//System.out.print("Cells Solved: " + startCellsDone);
			
			//Only check stuff if final value not assigned
			if (currentFinalValue == 0){
				Integer row = currentCell.get_rownum();
				Integer col = currentCell.get_colnum();
				Integer grid = currentCell.get_gridnum();
				
				//TODO:Only guess possible values
				for(int guess = 1; guess <10; ++guess){
					if(!isKeepSolving()){
						//System.out.println("returning false");
						return false;
					}
					//Check cell's row for it
					Integer checkValue = 0;
					Integer tmpCellnum = 0;
					//Step through cells in row/col/grid
					for (int rcg=0; rcg < 3; ++rcg){
						for (int j=0; j < 9; ++j){
							if(!isKeepSolving()){
								//System.out.println("returning false");
								return false;
							}
							if(rcg == 0){
								tmpCellnum = this._rows.get(row).get(j);
								//System.out.println("i: " + i + " row: " + row + " tmpCellnum: " + tmpCellnum);
							}
							else if (rcg == 1){
								tmpCellnum = this._cols.get(col).get(j);
								//System.out.println("i: " + i + " col: " + col + " tmpCellnum: " + tmpCellnum);
							}
							else if (rcg == 2){
								tmpCellnum = this._grids.get(grid).get(j);
								//System.out.println("i: " + i + " grid: " + grid + " tmpCellnum: " + tmpCellnum);
							}
							
							//System.out.println("tmpCellnum:" + tmpCellnum + " ");
							
							checkValue = this._allPuzzleCells.get(tmpCellnum).get_finalval();
							//Check cell row's cell value against guess value
							//If match, remove it from current cell, since it won't be that value.
							if(checkValue == guess && currentCell.get_possVal().contains(guess)){
								//Removes guess from both 
								//TODO:Step goes here
								if(this.stepPref){
									//Only do it once, for now
									//Still runs more than once since step is being set to true at top
									takeSolveStep(i, tmpCellnum, guess);

								}
								currentCell.remove_possVal(new Integer(guess));
							}
						}
					}
				}
			}
		
			//Check if there's only one possible value
			Cell tmp = this._allPuzzleCells.get(i); 
			if(tmp.num_possVal() <= 2){
				Vector<Integer> tmpVal = tmp.get_possVal();
				if(tmpVal.size() != 1){
					//System.out.println(tmp.get_possVal().size());
					tmpVal.remove(new Integer(0));
				}
				tmp.set_finalval(tmpVal.get(0));
			}
			this.keepSolving = true;
		}
		
		endCellsDone = getFinishedCells();
		
		if(endCellsDone == startCellsDone){
			//No new cells figured out, return false
			return false;
		}
		
		//A cell was updated, stage 1 was successful
		return true;
		
		//TODO:Setup preference whether or not they want to hide impossible choices
		
	}
	
	public void takeSolveStep(Integer first, Integer second, Integer guess){
		
		Cell firstCell = this._allPuzzleCells.get(first);
		Cell secondCell = this._allPuzzleCells.get(second);

		//Highlight Cells in question
		firstCell.change_bgcolor("YELLOW");
		secondCell.change_bgcolor("YELLOW");
		
		firstCell.show_possVal();

		String stepPrefString = CafeSudokuGUI.displayStepSolve(guess);
		if(stepPrefString.equals("YES")){
			this.setStepPref(true);
			this.setKeepSolving(true);
		}
		else if(stepPrefString.equals("NO")){
			this.setStepPref(false);
			this.setKeepSolving(true);
		}
		else if(stepPrefString.equals("CANCEL")){
			this.setStepPref(false);
			this.setKeepSolving(false);
		}
		
		firstCell.change_bgcolor("WHITE");
		secondCell.change_bgcolor("WHITE");
	}
	
	public boolean parseInputLine(String line, Integer currentRow){
		boolean allOk = true;
		Integer currentValue = 0;
		//System.out.println(text);
		try{
			String[] splitText = line.split(" ");
			for (int i=0; i < splitText.length; ++i){
				//System.out.println(splitText[i]);
				//_ official way to represent 0 in file
				if (splitText[i].equals("_")){
					//System.out.println("Setting CurrentValue to 0");
					currentValue = 0;
				}
				else{
					//Prevent a bunch of error messages about bad int parsing
					if (allOk){
						currentValue = Integer.parseInt(splitText[i]);
					}
				}

				Cell currentCell = this._allPuzzleCells.get((currentRow*9 + i));
				//TODO: Fix how possible values are initialized
				//currentCell.initializePossValues();
				
				currentCell.set_finalval(currentValue);
				currentCell.update_comboBoxSelected(currentValue);
			}
			return true;
		}
		catch(Exception e){
			//Not an Integer
			//System.out.println(allOk);
			CafeSudokuGUI.displayErrorMessage("File not properly formatted.\nPlease open a different file.");
			return false;
		}
	}
	
	public String get_rowOutput(int x){
		Vector<Integer> current = this._rows.get(x);
		String output = "";
		Integer value = -1;
		for(int i=0; i<current.size(); ++i){
			value = this._allPuzzleCells.get(current.get(i)).get_finalval();
			if(value == 0){
				output = output + "_";
			}
			else{
				output = output + this._allPuzzleCells.get(current.get(i)).get_finalval().toString();
			}
			if(i<current.size()-1){
				output = output + " ";
			}
		}
		
		output = output + "\n";
		
		return output;
	}
	

	public Cell get_puzzleCell(int i) {
		return _allPuzzleCells.get(i);
	}
	
	public int get_numCells(){
		return _allPuzzleCells.size();
	}

	public boolean isStepPref() {
		return stepPref;
	}

	public void setStepPref(boolean stepPref) {
		this.stepPref = stepPref;
	}

	public boolean isKeepSolving() {
		return keepSolving;
	}

	public void setKeepSolving(boolean keepSolving) {
		this.keepSolving = keepSolving;
	}

	public boolean isAlwaysCheckPref() {
		return alwaysCheckPref;
	}

	public void setAlwaysCheckPref(boolean alwaysCheckPref) {
		this.alwaysCheckPref = alwaysCheckPref;
	}

	public String get_filename() {
		return _filename;
	}

	public void set_filename(String _filename) {
		this._filename = _filename;
	}
}
