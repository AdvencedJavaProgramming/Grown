package com.Polodz.model;

import java.util.List;

public class Member implements IMember {
	private String name;
	private int id;
	private List<IItem> items;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<? extends IItem> getItems() {
		return items;
	}
	public List<? super Movie> addItems() {
		return items;
	}
	public void setItems(List<IItem> sims) {
		this.items = sims;
	}
}
