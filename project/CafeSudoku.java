package project;


public class CafeSudoku {

	CafeSudokuGUI _mainGUI;
	SudokuPuzzle _currentPuzzle;
	
	public static void main(String[] args) {
	
		System.out.println("Printing");

		SudokuPuzzle _currPuzzle = new SudokuPuzzle();
		CafeSudokuGUI _mainGUI = new CafeSudokuGUI();

		_mainGUI.syncPuzzleGUI(_currPuzzle);
	}
}
