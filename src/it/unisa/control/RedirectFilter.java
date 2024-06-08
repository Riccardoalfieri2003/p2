package it.unisa.control;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String page = httpRequest.getParameter("page");

        if (page != null && (page.equals("META-INF/context.xml") || page.equals("WEB-INF/web.xml")) && !isStaticResource(page)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/error.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String page) {
        return page.endsWith(".css") || page.endsWith(".js") || page.endsWith(".png") || page.endsWith(".jpg") || page.endsWith(".gif");
    }
}