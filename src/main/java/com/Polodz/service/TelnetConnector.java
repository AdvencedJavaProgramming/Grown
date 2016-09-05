package com.Polodz.service;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component("TelnetConnector")
public class TelnetConnector implements ITelnet {
	final ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("classpath:META-INF/tcp/tcpClientContext.xml");
	final GateAway gateway = context.getBean(GateAway.class);

	public TelnetConnector() {
		// TODO Auto-generated constructor stub
	}
	public String get(String input) {
		return  gateway.send(input);
	}
}
