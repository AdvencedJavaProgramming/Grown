package com.Polodz.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository("MembersDAOPrimitive")
public class MembersDAO implements IDao {
    private List<IMember> membersAudience = new ArrayList<>();

    public MembersDAO() {
        // TODO Auto-generated constructor stub
    }

    public List<IMember> getMembersAudience() {
        return membersAudience;
    }

    public void setMembers(List<IMember> membersAudience) {
        this.membersAudience = membersAudience;
    }

    public void addNode(IMember arg) {
        this.membersAudience.add(arg);
    }

    public IMember[] getALL() {
        return this.membersAudience.toArray(new IMember[this.membersAudience.size()]);
    }

}
