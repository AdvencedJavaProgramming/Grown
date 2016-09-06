package com.Polodz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Polodz.service.ITelnet;
import com.Polodz.service.TelnetConnector;

//@Component
public class Controller implements IController {
	private String lastListing;
	private ITelnet connector;//= new TelnetConnector(); 
	public Controller() {
	}
	@Autowired
	public Controller(ITelnet connector) {
		this.connector=connector;
	}
	@Override
	public String execute(String command) {
		this.lastListing = ((TelnetConnector) connector).get(command);
		return this.lastListing;
	}
	
	public String getLastListing() {
		return this.lastListing;
	}

}
