package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("nombre");
        String password = request.getParameter("pass");
        UsuarioDAO usuario = new UsuarioDAO();
        int usuarioValido = usuario.validarUs(email, password);
        switch (usuarioValido) {
            case 0:
                response.sendRedirect("/CACFLixFinal/gestionUsuarios.html");
                break;
            case 1:
                response.sendRedirect("/CACFLixFinal/index.html");
                break;
            default:
                response.sendRedirect("/CACFLixFinal/inicio.html");
                break;
        }
    }
}
