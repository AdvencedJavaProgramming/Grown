package com.Polodz.model;

public class Movie implements IItem {

	private String name;
	private Long id;
	private final Double price = Config.MovieTicketCost.getCost();
	private Integer soldTicketsNumer;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSoldTicketsNumber(Integer soldTickets) {
		this.soldTicketsNumer = soldTickets;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getSoldTicketsNumber() {
		return soldTicketsNumer;
	}

}
