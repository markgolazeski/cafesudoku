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
		if(isFinished(startFilled)){
			System.out.println("Can't start Solving:");
			System.out.println("This Puzzle is already Filled in.");
			return;
		}
		System.out.println("The Puzzle is starting with " + startFilled + " cells filled in.");
		//Puzzle is set up ok and not full
		//Begin solving with stage 1
		//TODO: set up booleans for subsequent stages and 
		runStage1();
		
		
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
		
	}
	

	public Cell get_puzzleCell(int i) {
		return _allPuzzleCells.get(i);
	}
	
	public int get_numCells(){
		return _allPuzzleCells.size();
	}
}
