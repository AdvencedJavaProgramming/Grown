package com.Polodz.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TelnetConnector implements ITelnet {
    final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/tcp/tcpClientContext.xml");
    final GateAway gateway = context.getBean(GateAway.class);

    public TelnetConnector() {
    }

    @Override
    public String get(String input) {
        return gateway.send(input);
    }
}
