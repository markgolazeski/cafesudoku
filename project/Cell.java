package project;
import java.util.Vector;


public class Cell {
	
	private Integer _rownum; //Row Cell belongs to
	private Integer _colnum; //Column Cell belongs to
	private Integer _gridnum; //Grid Cell belongs to
	
	private Integer _finalval; //Final value for Cell
	
	private Vector<Integer> _possvalues; //Vector that contains possible values
	
	
	public Cell(){
		System.out.println("Creating Cell()");
		_possvalues = new Vector<Integer>();
		
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
