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

        // Verifica se il percorso richiesto è uno dei pericoli
        if (page != null && (page.equals("META-INF/context.xml") || page.equals("WEB-INF/web.xml"))) {
            // Se lo è, reindirizza a una pagina sicura
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            System.out.println("Errore qui"); 
            httpResponse.sendRedirect("/error.jsp");
            return; // Termina qui per evitare che la richiesta venga inoltrata oltre
        }

        // Altrimenti, lascia passare la richiesta
        chain.doFilter(request, response);
    }
}