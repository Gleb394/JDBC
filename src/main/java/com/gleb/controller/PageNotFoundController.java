package com.gleb.controller;

import com.gleb.web.Requeast;
import com.gleb.web.ViewModel;

public class PageNotFoundController implements Controller {

    @Override
    public ViewModel process(Requeast requeast) {
        return ViewModel.of("404");
    }
}
