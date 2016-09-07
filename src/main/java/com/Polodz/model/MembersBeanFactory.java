package com.Polodz.model;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Polodz.controller.Controller;
import com.Polodz.controller.IController;

public class MembersBeanFactory implements FactoryBean<MembersDAO> {

    @Autowired
    private MembersFactory<MembersDAO> membersDaosFactory;
    private IController currentController;

    public MembersBeanFactory() {
        super();
    }

    public MembersBeanFactory(IController currentController) {
        super();
        this.currentController = currentController;
    }

    public MembersDAO getObject() throws Exception {
        return (MembersDAO) membersDaosFactory.create(currentController);
    }

    public Class<MembersDAO> getObjectType() {
        if ((membersDaosFactory == null) || (currentController == null)) {
            return null;
        }
        return membersDaosFactory.getType();
    }

    public boolean isSingleton() {
        return false;
    }

    public MembersFactory getBf() {
        return membersDaosFactory;
    }

    public void setMembersDaosFactory(MembersFactory membersDaosFactory) {
        this.membersDaosFactory = membersDaosFactory;
    }

}
