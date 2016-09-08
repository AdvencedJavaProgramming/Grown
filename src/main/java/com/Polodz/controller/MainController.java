package com.Polodz.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Polodz.View.MainWindow;
import com.Polodz.model.AuditRaport;
import com.Polodz.model.Config;
import com.Polodz.model.IItem;
import com.Polodz.model.IMember;
import com.Polodz.model.MembersDAO;
import com.Polodz.model.Movie;
import com.Polodz.service.WebService;

@Configuration
@org.springframework.stereotype.Controller
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

    @Bean
    public MainWindow getView() {
        if (mainView == null) {
            this.mainView = new MainWindow(this);
            if (membersDAO.getMembersAudience() != null)
                membersDAO.getMembersAudience()./*parallelStream().*/forEach(
                        cur -> {
                            String currentName = cur.getName();
                            String webStringBuffor = "";
                            int currentIndex = mainView.addToTree(currentName);
                            if (cur.getItems() != null)
                                for (IItem current : cur.getItems()) {
                                    mainView.addToSelectedSubTree(current.getName()
                                            , currentIndex);
                                    webStringBuffor += current.getName() + "\n";
                                }
                            if (StringUtils.isNotBlank(webStringBuffor)) {
                                this.setRentWebItems(webStringBuffor);
                            }
                        }
                );
        }
        return this.mainView;
    }

    public void setRentWebItems(String input) {
        this.webDataHandler.setMovieListString(input);
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
    
    public void returnItemTo(Long memberIdFrom, Integer indexItemFrom) {
    	IItem cacheMoveItem = this.findItemFromDao(memberIdFrom, indexItemFrom);
    	this.deleteMembersProduct(memberIdFrom, indexItemFrom);
    	this.insertMemberProduct(cacheMoveItem,(long)this.memberByIdFromDao(new Long(this.getMembersDAO().getALL().length-1)).getId());
    }

    @Override
    public void deleteMembersProduct(Long memberId, Integer indexItem) {
    	IMember delatingItemsMember = this.memberByIdFromDao(memberId);
        this.getServerResponse("delete"+findItemFromDao(memberId,indexItem));
        delatingItemsMember.getItems().remove((int) indexItem);
        if (this.membersDAO.getMembersAudience().size()-1==memberId) 
        	this.refreshWeb();
    }
    
    @Override
    public void insertMemberProduct(IItem itemToMove,Long memberIdDestination) {
        IMember addingItemsMember = this.membersDAO.getMembersAudience().get(memberIdDestination.intValue());
        this.getServerResponse("move" +itemToMove.getId() + " " +addingItemsMember.getId());
        addingItemsMember.addItems().add((Movie) itemToMove);
        if (this.membersDAO.getMembersAudience().size()-1==memberIdDestination)
        	this.refreshWeb();
    }   

    @Override
    public String getItemInfo(Long memberId, int index) {
        IItem chosenItem = this.membersDAO.getMembersAudience().get(memberId.intValue()).getItems().get(index);
        String bufforToWork = "";
        bufforToWork += "Status: \n Name: " + chosenItem.getName() + "\n";
        bufforToWork += "Ticket price: " + chosenItem.getPrice() + "\n";
        bufforToWork += "Audience: " + this.getServerResponse(chosenItem.getId().toString()) + "\n";
        //bufforToWork+="Rate: "+this.filmWebMovie.getRate()+"\n";
        //bufforToWork+="Interested: "+this.filmWebMovie.getInterested()+"\n";
        return bufforToWork;
    }

    @Override
    public String getAuditRaport() {
        AuditRaport raport = new AuditRaport();
        if (membersDAO.getMembersAudience() != null)
            membersDAO.getMembersAudience().parallelStream().forEach(
                    cur -> {
                        if (cur.getItems() != null)
                            cur.getItems().parallelStream().forEach(
                                    current -> {
                                        try {
                                            raport.addToAudience(Integer.valueOf(this.getServerResponse(current.getId().toString())));
                                        } catch (Exception e) {
                                            raport.addToErrorBuffor(current.getName() + "movie for id " + current.getId().toString() + "\n");
                                        }
                                    });

                    });
        else return Config.NoAuditToShow.getMessage();

        return raport.getRaportText();

    }
    
    private void refreshWeb() {
    	this.setRentWebItems(this.membersDAO.getALL()[membersDAO.getMembersAudience().size()-1]
        		.getItems().stream().map (i -> i.getName()).collect (Collectors.joining ("\n")));
    }
    
    public IItem findItemFromDao(Long memberId, Integer indexItem) {
        IMember foundItemsMember = this.memberByIdFromDao(memberId);
  		return foundItemsMember.getItems().get(indexItem);
    }
    
    public IMember memberByIdFromDao(Long memberId) {
    	return this.membersDAO.getMembersAudience().get(memberId.intValue());
    }

}
