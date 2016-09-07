package com.Polodz.controller;

import com.Polodz.View.MainWindow;

public interface IMainController {

	public MainWindow getView();
	public void setRentWebItems(String input);
	void deleteMembersProduct(Long memberId,Integer index);
	public String getServerResponse(String input);
	public String getItemInfo(Long long1, int index);
	public String getAuditRaport();
	

}
