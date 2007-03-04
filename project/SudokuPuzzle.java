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

	public Cell get_puzzleCell(int i) {
		return _allPuzzleCells.get(i);
	}
	
	public int get_numCells(){
		return _allPuzzleCells.size();
	}
}
