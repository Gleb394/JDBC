package com.gleb.web.filter;

import com.gleb.dao.UserDao;
import com.gleb.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.gleb.Factory.getUserDao;
import static com.gleb.web.filter.UserFilter.COOKIE_NAME;

public class LoginRegisterFilter implements Filter {

    private final Set<String> uriSet = new HashSet<>();

    private UserDao userDao;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = getUserDao();
        uriSet.add("/servlet/login");
        uriSet.add("/servlet/register");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        String token = null;
        User user = null;

        if (cookies == null) {
            chain.doFilter(request, response);
            return;
        }

        for (Cookie c : cookies) {
            if (c.getName().equals(COOKIE_NAME)) {
                token = c.getValue();
            }
        }

        if (token != null) {
            user = userDao.findByToken(token);
        }

        if (user != null && uriSet.contains(req.getRequestURI())) {
            req.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
