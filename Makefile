all: build

build:
	javac project/CafeSudoku.java

clean:
	rm -f ./project/CafeSudoku*.class
	rm -f ./project/SudokuPuzzle.class
	rm -f ./project/Cell*.class

run:
	java project.CafeSudoku
