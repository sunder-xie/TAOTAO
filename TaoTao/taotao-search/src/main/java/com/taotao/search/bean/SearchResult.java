package com.taotao.search.bean;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 定义jackson对象
	private Long total;
	private List<?> data;

	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public SearchResult(Long total, List<?> data) {
		super();
		this.total = total;
		this.data = data;
	}
	public SearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
}
