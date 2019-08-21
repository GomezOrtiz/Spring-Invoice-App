package com.cursoudemy.springboot.app.utils.pagination;

public class PageItem {
	
	private int num;
	private boolean isCurrent;
	
	public PageItem(int num, boolean isCurrent) {
		this.num = num;
		this.isCurrent = isCurrent;
	}

	public int getNum() {
		return num;
	}
	public boolean isCurrent() {
		return isCurrent;
	}
}
