package com.Polodz.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.Polodz.controller.IController;

public class MembersFactory<T extends IDao> implements IFactory {
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
        IDao result = null;
        try {
            result = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        arg.execute(" ");
        String membersNameList = arg.execute("list");
        String[] memberNameSourceLine = membersNameList.split("\\r?\\n");
        for (int i = 0; i < memberNameSourceLine.length; i++) {
            String memmbersName = memberNameSourceLine[i].split(" ")[0];
            Member memberToAdd = new Member();
            String itemsMemeber = arg.execute(memmbersName);
            List<IItem> newMemebersBasket = new ArrayList<>();
            memberToAdd.setItems(newMemebersBasket);
            if (itemsMemeber != null) {
                Arrays.stream(itemsMemeber.split("\\r?\\n")).forEach(itemName -> {
                    String[] itemNameProperties = itemName.split(" ");
                    Movie toAdd = new Movie();
                    toAdd.setName(itemNameProperties[0]);
                    toAdd.setId(new Long(itemNameProperties[1]));
                    memberToAdd.addItems().add(toAdd);
                });
            }
            memberToAdd.setId(i);
            memberToAdd.setName(memmbersName);
            result.addNode(memberToAdd);
        }
        return result;
    }

}
