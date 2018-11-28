package com.gleb.web;

import com.gleb.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.gleb.Factory.*;


public class MainServlet extends HttpServlet {

    private final static Map<Request, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put(Request.of("GET", "/servlet/categories"), getAllCategoriesController());
        controllerMap.put(Request.of("GET", "/servlet/register"), r -> ViewModel.of("register"));
        controllerMap.put(Request.of("GET", "/servlet/home"), r -> ViewModel.of("home"));
        controllerMap.put(Request.of("GET", "/servlet/login"), r -> ViewModel.of("login"));
        controllerMap.put(Request.of("GET", "/servlet/admin"), r -> ViewModel.of("adminPage"));
        controllerMap.put(Request.of("GET", "/servlet/admin/content"), r -> ViewModel.of("content"));
        controllerMap.put(Request.of("GET", "/servlet/admin/add-product"), getAllCategoriesAdminController("addProduct"));
        controllerMap.put(Request.of("GET", "/servlet/admin/categories"), getAllCategoriesAdminController("manageCategories"));
        controllerMap.put(Request.of("GET", "/servlet/admin/products"), getAllProductsAdminController());
        controllerMap.put(Request.of("GET", "/servlet/logout"), getLogOutController());
        controllerMap.put(Request.of("POST", "/servlet/login"), getLoginPageController());
        controllerMap.put(Request.of("POST", "/servlet/register"), getRegisterController());
        controllerMap.put(Request.of("POST", "/servlet/admin/add-product"), getAddProductController());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Request request = Request.of(req.getMethod(), req.getRequestURI(), req.getParameterMap(), req.getCookies());

        Controller controller = controllerMap.getOrDefault(request, r -> ViewModel.of("404"));

        ViewModel vm = null;
        try {
            vm = controller.process(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sendResponse(vm, req, resp);
    }

    private void sendResponse(ViewModel vm, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectUrl = "/WEB-INF/views/%s.jsp";
        vm.getModel().forEach(req::setAttribute);
        addCookie(vm, req, resp);
        req.getRequestDispatcher(String.format(redirectUrl, vm.getView())).forward(req, resp);
    }

    private void addCookie(ViewModel vm, HttpServletRequest req, HttpServletResponse resp) {
        if (vm.getCookies() != null) {
            for (Cookie c : vm.getCookies()) {
                if (c == null) {
                    continue;
                }
                resp.addCookie(c);
            }
        }
    }
}