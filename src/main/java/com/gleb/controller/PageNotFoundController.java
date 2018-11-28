package com.gleb.controller;

import com.gleb.web.Request;
import com.gleb.web.ViewModel;

public class PageNotFoundController implements Controller {
    @Override
    public ViewModel process(Request request) {
        return ViewModel.of("404");
    }


}
