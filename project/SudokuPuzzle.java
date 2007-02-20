package project;
import java.util.Vector;


public class SudokuPuzzle {

	private Vector<Cell> _puzzleCells;
	
	public SudokuPuzzle(){
		System.out.println("Creating SudokuPuzzle()");
		_puzzleCells = new Vector<Cell>();
		for (int i = 0; i < 9; ++i){
			for (int j = 0; j < 9; ++j){
				Cell a = new Cell();
				a.set_finalval(new Integer(0));
				this._puzzleCells.add(a);
			}
		}
	}

	public Cell get_puzzleCells(int i) {
		return _puzzleCells.get(i);
	}
}
