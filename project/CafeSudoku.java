package project;


public class CafeSudoku {

	protected CafeSudokuGUI _mainGUI;
	protected SudokuPuzzle _currPuzzle;
	
	public static void main(String[] args) {
		
		//SudokuPuzzle puzzle = new SudokuPuzzle();
		System.out.println("Printing");

		//GUI creates initial SudokuPuzzle
		CafeSudokuGUI _mainGUI = new CafeSudokuGUI();
		//_mainGUI.setCurrentPuzzle(puzzle);

		//_mainGUI.syncPuzzleGUI(puzzle);
	}
	
	public SudokuPuzzle getCurrentPuzzle(){
		return this._currPuzzle;
	}
}
