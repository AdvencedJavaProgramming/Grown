package com.Polodz.controller;

import com.Polodz.View.MainWindow;
import com.Polodz.model.IItem;

public interface IMainController {

    public MainWindow getView();

    public void setRentWebItems(String input);

    void deleteMembersProduct(Long memberId, Integer index);

    public String getServerResponse(String input);

    public String getItemInfo(Long long1, int index);

    public String getAuditRaport();

	void insertMemberProduct(IItem itemToMove, Long memberIdDestination);


}
