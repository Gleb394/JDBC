package com.gleb.controller;

import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.sql.SQLException;

public interface Controller {

    ViewModel process(Request request) throws SQLException;

}