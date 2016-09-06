package com.Polodz.service;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@Component("TelnetConnector")
public class TelnetConnector implements ITelnet {
	final ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("classpath:META-INF/tcp/tcpClientContext.xml");
	final GateAway gateway = context.getBean(GateAway.class);

	public TelnetConnector() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String get(String input) {
		return  gateway.send(input);
	}
}
