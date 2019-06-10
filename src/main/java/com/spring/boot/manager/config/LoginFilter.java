package com.spring.boot.manager.config;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = "/*")
@Order(1)
public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    private FilterConfig filterConfig;

    private String[] nofilters;
    private String[] filters;
    private String redirect;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (nofilters == null) {
            String nofilter = "user/login,page-login.html,swagger";
            String filter = "admin,.html";
            redirect = "page-login.html";
            nofilters = nofilter.split(",");
            filters = filter.split(",");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String url = request.getRequestURI();
        boolean is = this.isProcesssed(url);
        boolean not = this.isUnprocesssed(url);
        if (is && !not) {
            logger.debug("LoginFilter processed:" + url);
            if (session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + redirect);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            return;
        } else {
            logger.info("LoginFilter unprocessed:" + url);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {
        logger.debug("LoginFilter destroy!");
    }

    public boolean isUnprocesssed(String url) {
        for (String nf : nofilters) {
            if (StringUtils.isNotBlank(url) && url.contains(nf)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProcesssed(String url) {
        for (String f : filters) {
            if (StringUtils.isNotBlank(url) && url.contains(f)) {
                return true;
            }
        }
        return false;
    }

}
