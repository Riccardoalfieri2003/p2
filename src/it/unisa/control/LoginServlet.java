package it.unisa.control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao usDao = new UserDao();

        try {
            UserBean user = new UserBean();
            user.setUsername(request.getParameter("un"));
            user.setPassword(request.getParameter("pw"));
            user = usDao.doRetrieve(request.getParameter("un"),"");

            if (user != null && checkPassword(request.getParameter("pw"), user.getPassword())) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                String checkout = request.getParameter("checkout");
                if (checkout != null)
                    response.sendRedirect(request.getContextPath() + "/account?page=Checkout.jsp");
                else
                    response.sendRedirect(request.getContextPath() + "/Home.jsp");
            } else
                response.sendRedirect(request.getContextPath() + "/Login.jsp?action=error"); // error page
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    // Metodo per verificare la password
    private boolean checkPassword(String plainPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainPassword.getBytes());
            byte[] hashedBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            String hashedInputPassword = sb.toString();
            return hashedInputPassword.equals(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}