package com.cnce.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author changtuo 1992lcg@163.com
 */
public class PageUtils<o> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private List<?> rows;
	private o other;

	public PageUtils(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}
	public PageUtils(List<?> list, int total,o other) {
		this.rows = list;
		this.total = total;
		this.other=other;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public o getOther() {
		return other;
	}
	public void setOther(o other) {
		this.other = other;
	}

}
