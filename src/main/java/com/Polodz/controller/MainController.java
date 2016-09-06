package com.Polodz.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Polodz.View.MainWindow;
import com.Polodz.model.MembersFactory;
import com.Polodz.model.StringWebData;
import com.Polodz.model.IMember;
import com.Polodz.model.MembersDAO;
import com.Polodz.service.TelnetConnector;

@Configuration
@org.springframework.stereotype.Controller
//@Service("mainControlerService")!!!!
//@ComponentScan("com.Polodz.model")
public class MainController implements IMainController {
	private static final Logger log = Logger.getLogger(MainController.class.getName());
	public void test() {
		log.info("test");
	}
	@Autowired
	private MembersDAO membersDAO;
	
	@Autowired
	public IController telentController;
	
	@Autowired
	public WebMovieListController webDataHandler;
	
	@Autowired
	public MainWindow mainView;

	
	public MainController() {
		//this.setRentWebItems("test");
	}

	public MembersDAO getMembersDAO() {
		return membersDAO;
	}
	public void setMembersDAO(MembersDAO membersDAO) {
		this.membersDAO = membersDAO;
	}
	
	public IController getTelentController() {
		return telentController;
	}

	public void setTelentController(IController telentController) {
		this.telentController = telentController;
		//log.info(getMtcResponse("list"));
	}	
	
	@Bean
    public WebMovieListController getWebMovieListController() {
		return new WebMovieListController();
    }
	
	@Bean
	public MainWindow getView() {
		if (mainView==null) {
			this.mainView=new MainWindow(this);
			if (membersDAO.getMembersAudience()!=null)
				membersDAO.getMembersAudience()./*parallelStream().*/forEach(
						cur->{ 
							String currentName=cur.getName();
							String webStringBuffor=null;
							int currentIndex=mainView.addToTree(currentName);
							if (cur.getItems()!=null)
								cur.getItems()/*.parallelStream()*/.forEach(
										current->{ 
											mainView.addToSelectedSubTree(current.getName()
													,currentIndex);
//											if(currentName == "Main_Store") {
//												webStringBuffor+=current.getName()+"\n";
//											}
										});
							
							//if (webStringBuffor!=null) {
							//	this.setRentWebItems(webStringBuffor);
							//}
						}
						);
		}
		log.info(membersDAO.getALL().length);
        return this.mainView;
    }
	
//	@RequestMapping(method = RequestMethod.GET)
//	private ModelAndView Stringgg(String string) {
//		return new ModelAndView().addObject("testString", string);
//
//	}
//	
	public void setRentWebItems(String input) {
		StringWebData stringWebData = new StringWebData();
		stringWebData.setContent("tes");
//		this.webDataHandler.stringForm((Model) Stringgg("tes"));
		this.webDataHandler.stringSubmit(stringWebData);	
	}
	
	public String getServerResponse(String input) {
		return telentController.execute(input);
	}
	
	public String getLastServerResponse() {
		return ((Controller) telentController).getLastListing();
	}
	public List<IMember> listAll() {
		return Arrays.asList(membersDAO.getALL());
		
	}

	@Override
	public void deleteMembersProduct(Long memberId,Integer index) {
		this.membersDAO.getMembersAudience().get(memberId.intValue()).addItems().remove((int)index);
		
	}

}
