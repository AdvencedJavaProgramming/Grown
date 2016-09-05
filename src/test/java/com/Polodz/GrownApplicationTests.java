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
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.Polodz.View.MainWindow;
import com.Polodz.controller.Controller;
import com.Polodz.controller.IController;
import com.Polodz.controller.MainController;
import com.Polodz.model.MembersDAO;

import org.junit.Assert;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/test/resources/Grown-mainContext.xml"}, loader=CustomSpringApplicationContextLoader.class)
@SpringBootTest
@Configuration
//@ActiveProfiles("test")
public class GrownApplicationTests {
	private ClassPathXmlApplicationContext context;

	@Autowired
	private MainController mainControler;

	@Autowired
	private MembersDAO mtcDAO;
//	private BeanFactory BeanFactory;
	@Mock
	private IController IController;
	
	@Mock
	private MainWindow disableWindow;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this); 		
		when(IController.execute("list")).thenReturn("test\ntest\ntest\ntest\ntest\ntest");
		when(IController.execute("test items")).thenReturn("mtest\nmtest\nmtest\nmtest\nmtest\nmtest");
		reset(IController);
		//BeanFactory= context.getBean(BeanFactory.class);
	}

	@Test
	public void contextLoads() {
		Assert.assertEquals(mainControler.listAll().size(), 6);
		Assert.assertEquals(mainControler.listAll().get(1).getName(), "test");
		Assert.assertEquals(mainControler.getMtcResponse("list"), "test\ntest\ntest\ntest\ntest\ntest");
		//Assert.assertEquals(BeanFactory.getObjectType(), DAO.class);
	}
	
	@Bean
	@Primary
    public IController iController() {
        if (IController==null) {
        	IController=mock(Controller.class);
        	when(IController.execute("list")).thenReturn("test\ntest\ntest\ntest\ntest\ntest"
        			+ "");
        }
        return IController;
    }
	
	@Bean
	@Primary
    public MainWindow iMainWindow() {
        if (IController==null) {
        	disableWindow=mock(MainWindow.class);
        }
        
        return disableWindow;
    }

}
