package project;
import java.util.Vector;

import javax.swing.JComboBox;


public class Cell {
	
	private Integer _rownum; //Row Cell belongs to
	private Integer _colnum; //Column Cell belongs to
	private Integer _gridnum; //Grid Cell belongs to
	private static JComboBox _dropDownList;
	
	private Integer _finalval; //Final value for Cell
	
	private Vector<Integer> _possvalues; //Vector that contains possible values
	
	
	public Cell(){
		//System.out.println("Creating Cell()");
		initializePossValues();
	}
	
	public Cell(Integer row, Integer col){
		initializePossValues();
		this._rownum = row;
		this._colnum = col;
		//Formula to get grid number from row and col
		this._gridnum = 1 + (3 * ((row-1) / 3) + ((col-1) / 3)); 
		System.out.println("row: " + this._rownum + " col: " + this._colnum + " grid: " + this._gridnum);
	}
	
	public void initializePossValues(){
		this._possvalues = new Vector<Integer>();
		for(int i=0;i<10;++i)
		{
			this._possvalues.add(i);
		}
		
	}
	
	public Integer get_colnum() {
		return _colnum;
	}

	public void set_colnum(Integer _colnum) {
		this._colnum = _colnum;
	}

	public Integer get_gridnum() {
		return _gridnum;
	}

	public void set_gridnum(Integer _gridnum) {
		this._gridnum = _gridnum;
	}

	public Integer get_rownum() {
		return _rownum;
	}

	public void set_rownum(Integer _rownum) {
		this._rownum = _rownum;
	}

	public Integer get_finalval() {
		return _finalval;
	}
	
	public void set_finalval(Integer x){
		this._possvalues.clear();
		this._possvalues.add(x);
		
		this._finalval = x;
	}
	
}
