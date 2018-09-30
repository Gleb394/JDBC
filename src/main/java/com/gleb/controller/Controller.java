package com.gleb.controller;

import com.gleb.web.Requeast;
import com.gleb.web.ViewModel;

public interface Controller {

    ViewModel process(Requeast requeast);

}