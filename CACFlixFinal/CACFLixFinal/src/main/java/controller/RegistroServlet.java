package controller;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String documento = request.getParameter("documento");
        String nombres = request.getParameter("nombres");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String direccion = request.getParameter("direccion");
        String password = request.getParameter("pass");
        String estado = request.getParameter("estado");
        String tipo = request.getParameter("tipo");

        Usuario usuario = new Usuario();
        usuario.setDocumento(Long.parseLong(documento));
        usuario.setApellido(apellido);
        usuario.setNombres(nombres);
        usuario.setDireccion(direccion);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setEstado(Integer.parseInt(estado));
        usuario.setTipo(tipo);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            if (usuarioDAO.insertarUs(usuario)) {
                response.sendRedirect("/CACFLixFinal/inicio.html?exito=true");
            } else {
                response.sendRedirect("/CACFLixFinal/inicio.html?error=true");
            }
        } catch (IOException e) {
            response.sendRedirect("/CACFLixFinal/inicio.html?error=true");
        }
    }
}
