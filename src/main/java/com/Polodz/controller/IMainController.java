package com.Polodz.controller;

import com.Polodz.View.MainWindow;

public interface IMainController {

	public MainWindow getView();
	public void setRentWebItems(String input);
	void deleteMembersProduct(Long memberId,Integer index);

}
