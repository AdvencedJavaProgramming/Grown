package com.Polodz.model;

public enum Config {
	
    MovieTicketCost(12.0),
    NoAuditToShow("No any avalible Networ");
    private Double value;
    private String message;

    private Config(Double value) {
        this.value = value;
    }

    private Config(String message) {
        this.message = message;
    }

    public Double getCost() {
        return getValue();
    }

    public Double getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
    
}
