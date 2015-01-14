package com.szcmcc.movie.bean;

public class Point {
	public String row="";
	public String column="";
	public String lable="";
	public int type=1;
	
	public int rowNum=-1;
	public int colNUM=-1;
	
	public Point(){
		super();
	}
	
	public Point(String row, String column) {
		super();
		this.row = row;
		this.column = column;
	}
	public Point(String lable, int type) {
		super();
		this.lable = lable;
		this.type = type;
	}
	/**
	 * 生成坐标点
	 * @param row  行
	 * @param column 列
	 * @param lable  标签
	 * @param type   
	 */
	public Point(String row, String column, String lable, int type) {
		super();
		this.row = row;
		this.column = column;
		this.lable = lable;
		this.type = type;
	}
	
	
}
