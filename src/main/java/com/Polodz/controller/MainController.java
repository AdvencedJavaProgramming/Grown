package com.Polodz.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Polodz.View.MainWindow;
import com.Polodz.model.Config;
import com.Polodz.model.IItem;
import com.Polodz.model.IMember;
import com.Polodz.model.MembersDAO;
import com.Polodz.service.WebService;

@Configuration
@org.springframework.stereotype.Controller
//@Service("mainControlerService")!!!!
//@ComponentScan("com.Polodz.model")
public class MainController implements IMainController {
	private static final Logger log = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	private MembersDAO membersDAO;
	
	@Autowired
	private IController telnetController;
	
	@Autowired
	private WebService webDataHandler;
	
	@Autowired
	private MainWindow mainView;

	
	public MainController() {
		//this.setRentWebItems("test");
	}

	public MembersDAO getMembersDAO() {
		return membersDAO;
	}
	public void setMembersDAO(MembersDAO membersDAO) {
		this.membersDAO = membersDAO;
	}
	
	public IController getTelnetController() {
		return this.telnetController;
	}

	public void setTelnetController(IController telentController) {
		this.telnetController = telentController;
	}	
	
	/*@Bean
    public WebMovieListController getWebMovieListController() {
		return new WebMovieListController();
    }*/
	
	@Bean
	public MainWindow getView() {
		if (mainView==null) {
			this.mainView=new MainWindow(this);
			if (membersDAO.getMembersAudience()!=null)
				membersDAO.getMembersAudience()./*parallelStream().*/forEach(
						cur->{ 
							String currentName=cur.getName();
							String webStringBuffor="";
							log.info(currentName);
							int currentIndex=mainView.addToTree(currentName);
							if (cur.getItems()!=null)
								for (IItem current : cur.getItems()) {
									mainView.addToSelectedSubTree(current.getName()
											,currentIndex);
									//if(currentName == "Main_Store") {
										webStringBuffor+=current.getName()+"\n";
									//}
								}							
							if (webStringBuffor!=null) {
								this.setRentWebItems(webStringBuffor);
							}
						}
						);
		}
		log.info(membersDAO.getALL().length);
        return this.mainView;
    }
	
	public void setRentWebItems(String input) {
        webDataHandler.setMovieListString(input);
    }
	
	public String getServerResponse(String input) {
		return this.telnetController.execute(input);
	}
	
	public String getLastServerResponse() {
		return ((Controller) this.telnetController).getLastListing();
	}
	public List<IMember> listAll() {
		return Arrays.asList(membersDAO.getALL());
		
	}

	@Override
	public void deleteMembersProduct(Long memberId,Integer index) {
		IMember delatingItemsMember = this.membersDAO.getMembersAudience().get(memberId.intValue());
		this.getServerResponse(delatingItemsMember.getName()+ "delete");
		delatingItemsMember.getItems().remove((int)index);
		StringBuilder b = new StringBuilder();
//		b.append("\n");
//		this.membersDAO.getALL()[getMembersAudience().size()]
//				.getItems().stream().forEach(b::append);
//		
//		this.membersDAO.getALL()[membersDAO.getMembersAudience().size()].getItems().stream().
//		map (i -> i.getName()).collect (Collectors.joining ("\n"));
	}

	@Override
	public String getItemInfo(Long memberId, int index) {
		IItem chosenItem= this.membersDAO.getMembersAudience().get(memberId.intValue()).getItems().get(index);
		String bufforToWork=null;
		bufforToWork+="Status: \n Name: "+chosenItem.getName()+"\n";
		bufforToWork+="Ticket price: "+chosenItem.getPrice()+"\n";
		bufforToWork+="Audience: "+this.getServerResponse(chosenItem.getId().toString())+"\n";
		//bufforToWork+="Rate: "+this.filmWebMovie.getRate()+"\n";
		//bufforToWork+="Interested: "+this.filmWebMovie.getInterested()+"\n";
		return bufforToWork;
	}
	
	@Override
	public String getAuditRaport() {
		AuditRaport raport= new AuditRaport();
		if (membersDAO.getMembersAudience()!=null)
			membersDAO.getMembersAudience().parallelStream().forEach(
					cur->{ 
						if (cur.getItems()!=null)
							cur.getItems().parallelStream().forEach(
									current->{ 
										try {
											raport.addToAudience(Integer.valueOf(this.getServerResponse(current.getId().toString())));
										} catch (Exception e) {
											raport.addToErrorBuffor(current.getName()+"movie for id "+current.getId().toString()+ "\n");
										}
									});
						
					});
		else return Config.NoAuditToShow.getMessage();
		
		return raport.getRaportText();
		
	}

}
