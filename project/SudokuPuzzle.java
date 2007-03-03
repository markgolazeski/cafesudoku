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
		System.out.println("Creating SudokuPuzzle()");
		_allPuzzleCells = new Vector<Cell>();
		for (int i = 0; i < 9; ++i){
			for (int j = 0; j < 9; ++j){
				Cell a = new Cell(i,j);
				a.set_finalval(new Integer(0));
				this._allPuzzleCells.add(a);
			}
		}
	}

	public Cell get_puzzleCell(int i) {
		return _allPuzzleCells.get(i);
	}
}
