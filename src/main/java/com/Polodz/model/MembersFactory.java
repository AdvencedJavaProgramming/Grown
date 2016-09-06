package com.Polodz.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.Polodz.controller.Controller;
import com.Polodz.controller.IController;

@Component("BtcsFactory")
public class MembersFactory <T extends IDao> implements IFactory {
	private static final Logger log = Logger.getLogger(MembersFactory.class.getName());
	
	Class<T> type;

	public Class<T> getType() {
		return type;
	}
	public void setType(Class<T> type) {
		this.type = type;
	}

	public MembersFactory() {
		
	}
	@Override
	public IDao create(IController arg) {
		IDao result=null;
		try {
			result=type.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		arg.execute(" ");
		String membersNameList = arg.execute("list");
		//log.info(membersNameList+"Spis");
		String[] memberNameSrcLine= membersNameList.split("\\r?\\n");
		for (int i = 0; i < memberNameSrcLine.length; i++) {
			String memmbersName = memberNameSrcLine[i].split(" ")[0];
		    //log.info(memmbersName);
		    Member memberToAdd= new Member();
	    	String itemsMemeber = arg.execute(memmbersName);
	    	//log.info(memmbersName+itemsMemeber);
	    	List<IItem> newMemebersBasket= new ArrayList<>(); 
		    memberToAdd.setItems(newMemebersBasket);
		    if (itemsMemeber!=null) {
		    	Arrays.stream(itemsMemeber.split("\\r?\\n")).forEach( itemName -> {
			    	Movie toAdd = new Movie();
			    	toAdd.setName(itemName);
			    	memberToAdd.addItems().add(toAdd);
			    	log.info(itemName);
		    	});
	    	}
	    	//memberToAdd.getItems().forEach(action);
	    	//log.info(((Movie) memberToAdd.addItems().get(0)).getName()+"TOOOO");
	    	//log.info(memberToAdd.getItems().get(0).getName());
	    	memberToAdd.setName(memmbersName);
	    	result.addNode(memberToAdd);
		}
		return result;
	}

}
