package it.unisa.control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathTraversalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String page = httpRequest.getParameter("page");

        if (page != null && (page.contains("..") || page.contains("/")) && !isStaticResource(page)) {
            httpResponse.sendRedirect("error.jsp"); // reindirizza a una pagina di errore
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String page) {
        return page.endsWith(".css") || page.endsWith(".js") || page.endsWith(".png") || page.endsWith(".jpg") || page.endsWith(".gif");
    }

    @Override
    public void destroy() {}
}