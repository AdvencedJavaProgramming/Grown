package com.Polodz;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.Polodz.View.MainWindow;
import com.Polodz.controller.MainController;
import com.Polodz.model.IItem;
import com.Polodz.service.ITelnet;
import com.Polodz.service.TelnetConnector;

import org.junit.Assert;
import static org.mockito.Mockito.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/test/resources/Grown-mainContext.xml"}, loader=CustomSpringApplicationContextLoader.class)
@SpringBootTest
@Configuration
public class GrownApplicationTests {

	@Autowired
	private MainController mainControler;

	@Mock
	private ITelnet telnetHandler;
	
	@Mock
	private MainWindow disableWindow;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this); 		
		when(telnetHandler.get("list")).thenReturn("test\ntest1\ntest2\ntest3\ntest4\ntest5");
		when(telnetHandler.get("test")).thenReturn("mtest 1\nmtest1 2\nmtest2 3\nmtest3 4\nmtest4 5\nmtest5 6");
		reset(telnetHandler);
	}

	@Test
	public void membersLoad() {
		this.serverResponse();
		Assert.assertNotNull(mainControler.getMembersDAO().getMembersAudience());
		Assert.assertEquals(mainControler.listAll().size(), 6);
		Assert.assertEquals(mainControler.listAll().get(1).getName(), "test1");
		Assert.assertEquals(mainControler.listAll().get(1).getItems().get(1).getName(),"mtest1");
	}
	
	
	public void serverResponse() {
		Assert.assertEquals(mainControler.getServerResponse("list"), "test\ntest1\ntest2\ntest3\ntest4\ntest5");
	}
	
	@Test
	public void deleteMemberProduct() {
		Integer idOfFiredMember= mainControler.listAll().size()-1;
		List<? extends IItem> basket = mainControler.getMembersDAO().getMembersAudience().get(idOfFiredMember).getItems();
		Integer lastBasketSize = basket.size() - 2;
		mainControler.deleteMembersProduct (new Long(idOfFiredMember),0);
		mainControler.deleteMembersProduct (new Long(idOfFiredMember),4);
		Assert.assertEquals(lastBasketSize,new Integer(basket.size()));
	}
	
	@Bean
	@Primary
    public ITelnet TelnetConnector() {
        if (telnetHandler==null) {
        	telnetHandler=mock(TelnetConnector.class);
        	when(telnetHandler.get("list")).thenReturn("test\ntest1\ntest2\ntest3\ntest4\ntest5");
    		when(telnetHandler.get(startsWith("test"))).thenReturn("mtest 1\nmtest1 2\nmtest2 3\nmtest3 4\nmtest4 5\nmtest5 6");
        }
        return telnetHandler;
    }
	
	@Bean
	@Primary
    public MainWindow iMainWindow() {
        if (telnetHandler==null) {
        	disableWindow=mock(MainWindow.class);
        }
        
        return disableWindow;
    }

}
