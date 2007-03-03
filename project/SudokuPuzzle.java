package project;
import java.util.Vector;


public class SudokuPuzzle {

	private Vector<Cell> _allPuzzleCells;
	
	
	
	public SudokuPuzzle(){
		System.out.println("Creating SudokuPuzzle()");
		_allPuzzleCells = new Vector<Cell>();
		for (int i = 0; i < 9; ++i){
			for (int j = 0; j < 9; ++j){
				Cell a = new Cell();
				a.set_finalval(new Integer(0));
				this._allPuzzleCells.add(a);
			}
		}
	}

	public Cell get_puzzleCells(int i) {
		return _allPuzzleCells.get(i);
	}
}
