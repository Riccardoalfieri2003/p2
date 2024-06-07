package it.unisa.control;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String BASE_DIRECTORY = "/WebContent/"; // directory consentita
    private static final Set<String> DISALLOWED_FILES = Set.of("META-INF/context.xml", "WEB-INF/web.xml"); // file non consentiti

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");

        if (page == null || DISALLOWED_FILES.contains(page)) {
            response.sendRedirect("error.jsp"); // reindirizza a una pagina di errore
            return;
        }

        // Carica la pagina richiesta
        request.getRequestDispatcher("/" + page).forward(request, response);
    }
}