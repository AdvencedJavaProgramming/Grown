package com.Polodz.model;

import com.Polodz.controller.IController;

public interface IFactory {

    IDao create(IController arg);

}
