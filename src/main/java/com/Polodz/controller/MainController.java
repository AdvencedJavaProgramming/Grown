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

import com.Polodz.View.MainWindow;
import com.Polodz.model.MembersFactory;
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
	public MainWindow mainView;
	
	public MainController() {
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
	//@Bean
    public MainWindow frame() {
    	log.info(membersDAO.getALL().length);
		return this.mainView;
    }
	
	@Bean
	public MainWindow getView() {
		if (mainView==null) {
			this.mainView=new MainWindow(this);
			if (membersDAO.getMembersAudience()!=null)
				membersDAO.getMembersAudience()./*parallelStream().*/forEach(
						cur->{ 
							int currentIndex=mainView.addToTree(cur.getName());
							if (cur.getItems()!=null)
								cur.getItems()/*.parallelStream()*/.forEach(
										current->mainView
											.addToSelectedSubTree(current.getName()
													,currentIndex)
										);
						}
						);
		}
		log.info(membersDAO.getALL().length);
        return this.mainView;
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
		this.membersDAO.getMembersAudience().get(memberId.intValue()).addItems().remove(index);
		
	}

}
