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
		//System.out.println("rows: " + this._rows.toString());
		//System.out.println("cols: " + this._cols.toString());
		//System.out.println("grids: " + this._grids.toString());
		
		/*for (int i=0; i<9;++i){
			System.out.println("row(0),spot(" + i +"): " + this._rows.elementAt(0).get(i).toString());
		}*/
	}
	
	public boolean isFinished(Integer x){
		if(x < this._allPuzzleCells.size()){
			return false;
		}
		return true;
	}
	
	//Checks if a puzzle has valid entries
	//TODO: Highlight bad value(s)
	public boolean isValid(){
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
						System.out.println("There is a conflict in " + inCurrently + " " + (i%9 + 1) + ".");
						return false;
					}
				}
				//System.out.println("count: " + count);
			}
		}
		return true;
	}
	
	//General solving
	public void solve(){
		if (!this.isValid()){
			System.out.println("Can't start Solving:");
			System.out.println("There are conflicts in the starting puzzle.");
			return;
		}
		Integer startFilled = getFinishedCells();
		/*if(isFinished(startFilled)){
			System.out.println("Can't start Solving:");
			System.out.println("This Puzzle is already Filled in.");
			return;
		}*/
		System.out.println("The Puzzle is starting with " + startFilled + " cells filled in.");
		//Puzzle is set up ok and not full
		//Begin solving with stage 1
		
		boolean keepSolving = true;
		Integer finishedCells;
		//TODO: set up booleans for subsequent stages and
		while (keepSolving){
			keepSolving = false;//run it once
			runStage1();
			finishedCells = getFinishedCells();
			//System.out.println(this._allPuzzleCells.size());
			if(finishedCells == this._allPuzzleCells.size()){
				keepSolving = false;
				System.out.println("Solved!");
			}
		}
		
		
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
	
	public void runStage1(){
		
		Integer currentFinalValue = 0;
		for (int i=0;i<this._allPuzzleCells.size(); ++i){
			Cell currentCell = this._allPuzzleCells.get(i);
			System.out.print("Cell: " + i + " vals: ");
			currentCell.dump_possVal();
			currentFinalValue = currentCell.get_finalval();
			
			
			//Only check stuff if final value not assigned
			if (currentFinalValue == 0){
				Integer row = currentCell.get_rownum();
				Integer col = currentCell.get_colnum();
				Integer grid = currentCell.get_gridnum();
				
				//TODO:Only guess possible values
				for(int guess = 1; guess <10; ++guess){
					//Check cell's row for it
					Integer checkValue = 0;
					Integer tmpCellnum = 0;
					//Step through cells in row/col/grid
					for (int rgc=0; rgc < 3; ++rgc){
						for (int j=0; j < 9; ++j){
							if(rgc == 0){
								tmpCellnum = this._rows.get(row).get(j);
							}
							else if (rgc == 1){
								tmpCellnum = this._rows.get(col).get(j);
							}
							else if (rgc == 2){
								tmpCellnum = this._rows.get(grid).get(j);
							}
							
							System.out.print("tmpCellnum:" + tmpCellnum + " ");
							
							checkValue = this._allPuzzleCells.get(tmpCellnum).get_finalval();
							//if(tmpCellnum == 1){
								
							//	System.out.println("Cellnum = " + tmpCellnum + " finalValue: " + checkValue + " guess: " + guess);
							//}
							//Check cell row's cell value against guess value
							//If match, remove it from current cell, since it won't be that value.
							if(checkValue == guess){
								//Removes guess from both 
								/*if (tmpCellnum < 9){
									System.out.println("Removing: " + guess + " From CellNumber: " + tmpCellnum);
									System.out.println("rgc: " + rgc + " row: " + row + " col: " + col + " grid: " + grid );
								}*/
								
								currentCell.remove_possVal(new Integer(guess));
							}
						}
						System.out.println("");
					}
				}
			}
		}
		
		//TODO:Setup preference whether or not they want to hide impossible choices
		
	}
	
	public void parseInputLine(String line, Integer currentRow){
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
					currentValue = Integer.parseInt(splitText[i]);
				}
				this._allPuzzleCells.get((currentRow*9 + i)).set_finalval(currentValue);
				this._allPuzzleCells.get((currentRow*9 + i)).update_comboBoxSelected(currentValue);
			}
		}
		catch(Exception e){
			//Not an Integer
		}
	}
	

	public Cell get_puzzleCell(int i) {
		return _allPuzzleCells.get(i);
	}
	
	public int get_numCells(){
		return _allPuzzleCells.size();
	}
}
