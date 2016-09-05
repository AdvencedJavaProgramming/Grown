package com.Polodz;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.support.DaoSupport;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.Polodz.controller.Controller;
import com.Polodz.controller.IController;
import com.Polodz.controller.MainController;
import com.Polodz.model.MembersBeanFactory;
import com.Polodz.model.MembersDAO;

import junit.framework.Assert;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("deprecation")
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringJUnit4ClassRunner.class)//(SpringJUnit4ClassRunner.class)//(SpringRunner.class)
@ContextConfiguration(locations={"file:src/test/resources/Grown-mainContext.xml"}, loader=CustomSpringApplicationContextLoader.class)
@SpringBootTest
//@ActiveProfiles("test")
//@ContextConfiguration(locations = {"classpath*:spring/mocksContext.xml"})
//@ContextConfiguration
//@ActiveProfiles("test")
@Configuration
public class GrownApplicationTests {
	private ClassPathXmlApplicationContext context;
	//@InjectMocks
	@Autowired
	private MainController mainControler;
	//@InjectMocks
	@Autowired
	private MembersDAO mtcDAO;
//	private BtcsBeanFactory btcsBeanFactory;
	@Mock
	private IController IController;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this); //@RunWith(MockitoJUnitRunner.class).
		//context= new ClassPathXmlApplicationContext("file:src/EasyMTC-contextTestBF.xml");//file:classpath*
		//mtcDAO= context.getBean("mtcDAOF",MtcDAO.class);
		//mainControler= context.getBean("mainController",MainController.class);
		//mtcDAO= (MtcDAO)context.getBean("mtcDAO");
		
		when(IController.execute("list")).thenReturn("test\ntest\ntest\ntest\ntest\ntest");
		when(IController.execute("test items")).thenReturn("mtest\nmtest\nmtest\nmtest\nmtest\nmtest");
		reset(IController);
		//btcsBeanFactory= context.getBean(BtcsBeanFactory.class);
	}
	@SuppressWarnings("deprecation")
	@Test
	public void contextLoads() {
		Assert.assertEquals(mainControler.listAll().size(), 6);
		Assert.assertEquals(mainControler.listAll().get(1).getName(), "test");
		Assert.assertEquals(mainControler.getMtcResponse("list"), "test\ntest\ntest\ntest\ntest\ntest");
		//Assert.assertEquals(mtcDAO.getALL().length, 86);
		//Assert.assertEquals(mtcDAO.getALL()[85].getName(), "bsc183"); //Aware of spaces!
		//Assert.assertEquals(btcsBeanFactory.getObjectType(), MtcDAO.class);
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

}
